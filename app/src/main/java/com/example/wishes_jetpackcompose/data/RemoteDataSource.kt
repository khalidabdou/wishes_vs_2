package com.example.wishes_jetpackcompose.data


import com.example.wishes_jetpackcompose.data.entities.Ads
import com.example.wishes_jetpackcompose.data.entities.Apps
import com.example.wishes_jetpackcompose.data.entities.Categories
import com.example.wishes_jetpackcompose.data.entities.Images
import com.example.wishes_jetpackcompose.utlis.Const.Companion.LANGUAGE_ID
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val wallApi: Api
) {
    // images
    suspend fun getImages(): Response<Images> {
        return wallApi.getImages(LANGUAGE_ID)
    }

    suspend fun getCategories(): Response<Categories> {
        return wallApi.getImgesCategories(LANGUAGE_ID)
    }

    suspend fun incShareImg(id: Int?) {
        return wallApi.incShareImg(id)
    }

    suspend fun incViewsImg(id: Int) {
        wallApi.incViewsImg(id)
    }

    suspend fun incDownloadImg(id: Int) {
        wallApi.incDownloadImg(id)
    }

    suspend fun incFavImg(id: Int) {
        wallApi.incFavImg(id)
    }
    //---- images --------

    suspend fun incShareQuote(id: Int) {
        wallApi.incShareQuote(id)
    }

    suspend fun incViewsQuote(id: Int) {
        wallApi.incViewsQuote(id)
    }

    suspend fun incFavQuote(id: Int) {
        wallApi.incFavQuote(id)
    }
    //---- end quotes-------

    suspend fun getApps(): Response<Apps> {
        return wallApi.getApps()
    }

    //get ads
    suspend fun getAds(): Response<Ads> {
        return wallApi.getAds()
    }


}