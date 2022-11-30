package com.wishes.jetpackcompose.viewModel


import android.app.Application
import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.*
import com.example.wishes_jetpackcompose.data.entities.Categories
import com.example.wishes_jetpackcompose.data.entities.Category
import com.example.wishes_jetpackcompose.data.entities.Image
import com.example.wishes_jetpackcompose.data.entities.Images
import com.example.wishes_jetpackcompose.repo.ImagesRepo
import com.example.wishes_jetpackcompose.utlis.Const.Companion.LANGUAGE_ID
import com.example.wishes_jetpackcompose.utlis.Const.Companion.hasConnection
import com.example.wishes_jetpackcompose.utlis.NetworkResults
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject
import com.example.wishes_jetpackcompose.utlis.Const.Companion.RANDOM
import java.util.*


@HiltViewModel
class ImagesViewModel @Inject constructor(
    private val imageRepo: ImagesRepo,
    application: Application
) : AndroidViewModel(application) {
    val LOG_IMAGE: String = "response_image"

    /** RETROFIT **/
    var categories: MutableLiveData<NetworkResults<Categories>> = MutableLiveData()
    var images: MutableLiveData<NetworkResults<Images>> = MutableLiveData()

    var offset by mutableStateOf(30)



    /**ROOM DATABASE**/
    var catId: Int = 0
    var imageslist by mutableStateOf(emptyList<Image>())
    var categoriesList by mutableStateOf(emptyList<Category>())
    var favoritesList by mutableStateOf(emptyList<Image>())
    var imagesByCategory by mutableStateOf(emptyList<Image>())



    fun getImagesRoom()=viewModelScope.launch(Dispatchers.IO){
        imageRepo.local.getImages(LANGUAGE_ID).collect{
            if (it.isEmpty()){
                getImages()
            }else imageslist=it.shuffled(Random(RANDOM)).subList(0,400)
        }
    }

    fun getCategoriesRoom()=viewModelScope.launch(Dispatchers.IO){
        imageRepo.local.readCategories("image", LANGUAGE_ID).collect{
            categoriesList=it.shuffled(Random(100))
            if (it.isEmpty()){
                getCategories()
            }
        }
    }

    fun getFavoritesRoom()=viewModelScope.launch(Dispatchers.IO){
        imageRepo.local.getFavoriteImages().collect{
            favoritesList=it
        }
    }

    fun getByCatRoom(id:Int)=viewModelScope.launch(Dispatchers.IO){
        imageRepo.local.getImagesByCat(id, LANGUAGE_ID).collect{
            imagesByCategory=it
        }
    }

    //val readImages: LiveData<List<Image>> = imageRepo.local.getImages(LANGUAGE_ID).asLiveData()
    val readCategories: LiveData<List<Category>> =
        imageRepo.local.readCategories("image", LANGUAGE_ID).asLiveData()
    val favorites: LiveData<List<Image>> = imageRepo.local.getFavoriteImages().asLiveData()


    private fun cacheCategories(categories: List<Category>) =
        viewModelScope.launch(Dispatchers.IO) {
            imageRepo.local.insertCategories(categories)
        }

    fun incDownloadImg(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        imageRepo.remot.incDownloadImg(id)
    }

    fun incShareImg(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        imageRepo.remot.incShareImg(id)
    }

    fun incFavImg(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        imageRepo.remot.incFavImg(id)
    }

    fun incViewImg(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        imageRepo.remot.incViewsImg(id)
    }

    fun getCategories() {
        viewModelScope.launch {
            getCategoriesSafeCall()
        }
    }

    private suspend fun getCategoriesSafeCall() {
        if (hasInternetConnection()) {
            try {
                val categoriesResponse = imageRepo.remot.getCategories()
                categories.value = handCategoriesResponse(categoriesResponse)
                Log.d("Tag_quote", categoriesResponse.body().toString())
            } catch (ex: Exception) {
                categories.value = NetworkResults.Error(ex.message)
            }
        } else {
            categories.value = NetworkResults.Error("No Internet Connection")
        }

        //categories.value=NetworkResults.Loading()
    }

    private fun handCategoriesResponse(categoriesResponse: Response<Categories>): NetworkResults<Categories>? {
        when {
            categoriesResponse.message().toString()
                .contains("Timeout") -> return NetworkResults.Error("Timeout")
            categoriesResponse.code() == 402 -> return NetworkResults.Error("Api Key Limited.")
            categoriesResponse.body()!!.listCategory.isNullOrEmpty() -> return NetworkResults.Error(
                "No Data Found"
            )
            categoriesResponse.isSuccessful -> {
                val categories = categoriesResponse.body()
                categories!!.listCategory.forEach { cat -> cat.type = "image" }
                cacheCategories(categories.listCategory)
                Log.d("Tag_quotes", readCategories.value.toString())

                return NetworkResults.Success(categories)
            }
            else -> return NetworkResults.Error(categoriesResponse.message())
        }
    }

    fun addToFav(id: Int, fav: Int) {
        viewModelScope.launch {
            imageRepo.local.addToFav(id, fav)
        }
    }

    fun getImages() {
        viewModelScope.launch {
            getImagesSafeCall()
        }
    }

    private suspend fun getImagesSafeCall() {
        if (hasInternetConnection()) {
            try {
                val imagesResponse = imageRepo.remot.getImages()
                images.value = handlImagesResponse(imagesResponse)
                val imageCache = images.value!!.data
                if (imageCache != null) {
                    offlineCacheImages(imageCache.results)
                    Log.d("Tag_quotes", imageCache.results.toString())
                }
            } catch (ex: Exception) {
                Log.d(LOG_IMAGE, ex.toString())
                images.value = NetworkResults.Error(ex.message)
            }

        } else {
            images.value = NetworkResults.Error("No Internet Connection")
            //FirebaseCrashlytics.getInstance().log("Images : No Internet Connection")
        }

    }

    private fun offlineCacheImages(images: List<Image>) {
        viewModelScope.launch(Dispatchers.IO) {
            imageRepo.local.updateOrInsert(images)
        }
    }

    private fun handlImagesResponse(imagesResponse: Response<Images>): NetworkResults<Images>? {
        when {
            imagesResponse.message().toString()
                .contains("Timeout") -> return NetworkResults.Error("Timeout")
            imagesResponse.code() == 402 -> return NetworkResults.Error("Api KEy Limited.")
            imagesResponse.body()!!.results.isNullOrEmpty() -> {
                return NetworkResults.Error("No Data Found")
            }
            imagesResponse.isSuccessful -> {
                val images = imagesResponse.body()
                return NetworkResults.Success(images!!)
            }
            else -> return NetworkResults.Error(imagesResponse.message())
        }
    }

    fun deleteCategories(id: Int) {
        viewModelScope.launch {
            //imageRepo.local.deleteCategory(id)
        }
    }

    fun deleteImage(id: Int) {
        viewModelScope.launch {
            // imageRepo.local.deleteImage(id)
        }
    }


    fun hasInternetConnection(): Boolean = hasConnection(getApplication())

}