package com.wishes.jetpackcompose.data


import com.wishes.jetpackcompose.BuildConfig
import com.wishes.jetpackcompose.data.entities.Categories
import com.wishes.jetpackcompose.data.entities.Images
import com.wishes.jetpackcompose.data.entities.LanguageApp
import com.wishes.jetpackcompose.data.entities.Languages
import com.wishes.jetpackcompose.utlis.Const.Companion.LANGUAGE_ID
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val wallApi: Api
) {
    // images
    suspend fun getImages(languageApp: Int): Response<Images?>? {
        return wallApi.getImages(languageApp)
    }

    suspend fun getCategories(): Response<Categories?> {
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


    //get ads
    suspend fun getAds()=wallApi.getAds(BuildConfig.PACKAGE_NAME)


    suspend fun getLanguages(): Response<Languages?>? = wallApi.getLanguages()

}