
package com.example.wishes_jetpackcompose.data
import android.animation.FloatEvaluator
import com.example.wishes_jetpackcompose.data.entities.Category
import com.example.wishes_jetpackcompose.data.entities.Image


import kotlinx.coroutines.flow.Flow
import org.intellij.lang.annotations.Language
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

    fun getImagesByCat(id: Int,language: Int): Flow<List<Image>> {
        return WallDao.getImagesByCat(id,language)
    }

    suspend fun insertImages(images: List<Image>) {
        WallDao.insertImages(images)
    }

    suspend fun insertImage(image: Image) {
        WallDao.insertImage(image)
    }

    fun readCategories(type: String,language: Int): Flow<List<Category>> {
        return WallDao.readCategories(type,language)
    }

    suspend fun insertCategories(categories: List<Category>) {
        WallDao.insertCategories(categories)
    }


    suspend fun addToFav(id: Int, fav: Int) {
        WallDao.addToFav(id, fav)
    }

    fun readImageByCategory(catID: Int,language: Int): Flow<List<Image>> {
        return WallDao.readImagesByCategory(catID,language)
    }

}