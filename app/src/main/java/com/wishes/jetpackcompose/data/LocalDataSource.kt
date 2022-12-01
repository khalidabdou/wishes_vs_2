package com.wishes.jetpackcompose.data



import com.wishes.jetpackcompose.data.entities.Category
import com.wishes.jetpackcompose.data.entities.Image
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val WallDao: IDao,
) {

    /**fro images **/
    fun getImages(language: Int): Flow<List<Image>> {
        return WallDao.getImages(language)
    }

    suspend fun updateOrInsert(images: List<Image>) {
        return WallDao.insertOrUpdate(images)
    }

    fun getFavoriteImages(): Flow<List<Image>> {
        return WallDao.getFavoriteImages()
    }

    fun getImagesByCat(id: Int, language: Int): Flow<List<Image>> {
        return WallDao.getImagesByCat(id, language)
    }

    suspend fun insertImages(images: List<Image>) {
        WallDao.insertImages(images)
    }

    suspend fun insertImage(image: Image) {
        WallDao.insertImage(image)
    }

    fun readCategories(type: String, language: Int): Flow<List<Category>> {
        return WallDao.readCategories(type, language)
    }

    suspend fun insertCategories(categories: List<Category>) {
        WallDao.insertCategories(categories)
    }


    suspend fun addToFav(id: Int, fav: Int) {
        WallDao.addToFav(id, fav)
    }

    fun readImageByCategory(catID: Int, language: Int): Flow<List<Image>> {
        return WallDao.readImagesByCategory(catID, language)
    }

}