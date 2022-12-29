package com.wishes.jetpackcompose.viewModel


import android.app.Application
import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.*
import com.wishes.jetpackcompose.R
import com.wishes.jetpackcompose.data.entities.*
import com.wishes.jetpackcompose.data.entities.AdProvider.Companion.Banner
import com.wishes.jetpackcompose.data.entities.AdProvider.Companion.BannerApplovin
import com.wishes.jetpackcompose.data.entities.AdProvider.Companion.BannerFAN
import com.wishes.jetpackcompose.data.entities.AdProvider.Companion.Inter
import com.wishes.jetpackcompose.data.entities.AdProvider.Companion.InterApplovin
import com.wishes.jetpackcompose.data.entities.AdProvider.Companion.InterFAN
import com.wishes.jetpackcompose.data.entities.AdProvider.Companion.OpenAd
import com.wishes.jetpackcompose.data.entities.AdProvider.Companion.Rewarded
import com.wishes.jetpackcompose.repo.RepositoryImpl

import com.wishes.jetpackcompose.utlis.Const.Companion.LANGUAGE_ID
import com.wishes.jetpackcompose.utlis.Const.Companion.LANGUAGE_PREFERENCE
import com.wishes.jetpackcompose.utlis.Const.Companion.hasConnection
import com.wishes.jetpackcompose.utlis.HandleResponse
import com.wishes.jetpackcompose.utlis.NetworkResults
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import retrofit2.Response
import java.util.*
import javax.inject.Inject


@HiltViewModel
class ImagesViewModel @Inject constructor(
    private val imageRepo: RepositoryImpl,
    application: Application
) : AndroidViewModel(application) {
    val LOG_IMAGE: String = "response_image"

    /** RETROFIT **/
    var categories: MutableLiveData<NetworkResults<Categories>> = MutableLiveData()
    var images: MutableLiveData<NetworkResults<Images>> = MutableLiveData()

    var offset by mutableStateOf(30)

    val infos = mutableStateOf<NetworkResults<Ads>>(NetworkResults.Loading())
    val apps = mutableStateOf<List<App>?>(null)

    private val _message = MutableStateFlow<String>("Good Morning")
    val message: StateFlow<String> get() = _message

    //language
    var languageID = getLanguage()

    /**ROOM DATABASE**/
    var catId: Int = 0
    var imageslist by mutableStateOf(emptyList<Image>())
    var categoriesList by mutableStateOf(emptyList<Category>())
    var favoritesList by mutableStateOf(emptyList<Image>())
    var imagesByCategory by mutableStateOf(emptyList<Image>())


    fun getImagesRoom() = viewModelScope.launch(Dispatchers.IO) {
        imageRepo.local.getImages(LANGUAGE_ID).collect {
            if (it.isEmpty()) {
                getImages()
            } else {
                if (imageslist.isEmpty())
                    imageslist = it.subList(0, 400).shuffled()
            }
            //Log.d("RANDOM", RANDOM.toString())
        }
    }

    fun getCategoriesRoom() = viewModelScope.launch(Dispatchers.IO) {
        imageRepo.local.readCategories("image", LANGUAGE_ID).collect {
            categoriesList = it.shuffled(Random(100))
            if (it.isEmpty()) {
                getCategories()
            }
        }
    }

    fun getFavoritesRoom() = viewModelScope.launch(Dispatchers.IO) {
        imageRepo.local.getFavoriteImages().collect {
            favoritesList = it
        }
    }

    fun getByCatRoom(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        imageRepo.local.getImagesByCat(id, LANGUAGE_ID).collect {
            imagesByCategory = it
        }
    }

    //val readImages: LiveData<List<Image>> = imageRepo.local.getImages(LANGUAGE_ID).asLiveData()
    private val readCategories: LiveData<List<Category>> =
        imageRepo.local.readCategories("image", LANGUAGE_ID).asLiveData()
    val favorites: LiveData<List<Image>> = imageRepo.local.getFavoriteImages().asLiveData()


    private fun cacheCategories(categories: List<Category>) =
        viewModelScope.launch(Dispatchers.IO) {
            imageRepo.local.insertCategories(categories)
        }

    fun incDownloadImg(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        imageRepo.remote.incDownloadImg(id)
    }

    fun incShareImg(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        imageRepo.remote.incShareImg(id)
    }

    fun incFavImg(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        imageRepo.remote.incFavImg(id)
    }

    fun incViewImg(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        imageRepo.remote.incViewsImg(id)
    }

    private fun getCategories() {
        viewModelScope.launch {
            getCategoriesSafeCall()
        }
    }

    fun getAds() = viewModelScope.launch {
        if (infos.value is NetworkResults.Error) {
            //Log.e("ads", ads.value.toString())
        }
        if (infos.value is NetworkResults.Loading) {
            try {
                val response = imageRepo.remote.getAds()
                val handle = HandleResponse(response)
                infos.value = handle.handleResult()

                apps.value = infos.value.data!!.apps
                infos.value.data!!.ads.forEach {
                    Log.d("FAN", it.ad_id)
                    when (it.type) {
                        "banner" -> {
                            Banner = it
                            Log.d("ads", Banner.toString())
                        }
                        "inter" -> {
                            Inter = it
                            //Log.d("ads", Inter.toString())
                        }
                        "open" -> {
                            Log.d("FAN", it.ad_id)
                            OpenAd = it
                            //Log.d("ads", OpenAd.toString())
                        }
                        "rewarded" -> {
                            Rewarded = it
                            //Log.d("ads", OpenAd.toString())
                        }
                        "banner_fan" -> {
                            Log.d("FAN", it.ad_id)
                            BannerFAN = it
                            //Log.d("ads", Banner.toString())
                        }
                        "inter_fan" -> {
                            InterFAN = it
                            //Log.d("ads", Inter.toString())
                        }
                        "banner_applovin" -> {
                            Log.d("FAN", it.ad_id)
                            BannerApplovin = it
                            //Log.d("ads", Banner.toString())
                        }
                        "inter_Applovin" -> {
                            InterApplovin = it
                            //Log.d("ads", Inter.toString())
                        }
                    }
                }

            } catch (ex: Exception) {
                Log.d("Exception", ex.toString())
            }
        }
    }

    private suspend fun getCategoriesSafeCall() {
        if (hasInternetConnection()) {
            try {
                val categoriesResponse = imageRepo.remote.getCategories()
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

    private fun getImages() {
        viewModelScope.launch {
            getImagesSafeCall()
        }
    }

    private suspend fun getImagesSafeCall() {
        if (hasInternetConnection()) {
            try {
                val imagesResponse = imageRepo.remote.getImages()
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

    fun saveLanguage(value: Int) {
        viewModelScope.launch {
            imageRepo.dataStore.putInt(LANGUAGE_PREFERENCE, value)
        }
    }

    fun getLanguage(): Int? = runBlocking {
        imageRepo.dataStore.getInt(LANGUAGE_PREFERENCE)
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


    fun setMessage(context: Context) {
        val c: Calendar = Calendar.getInstance()
        val timeOfDay: Int = c.get(Calendar.HOUR_OF_DAY)
        when (timeOfDay) {
            in 0..11 -> {
                _message.value = context.getString(R.string.morning)
            }
            in 12..15 -> {
                _message.value = context.getString(R.string.afternoon)
            }
            in 16..20 -> {
                _message.value = context.getString(R.string.evening)
            }
            in 21..23 -> {
                _message.value = context.getString(R.string.night)
            }

        }

    }


    fun hasInternetConnection(): Boolean = hasConnection(getApplication())

}