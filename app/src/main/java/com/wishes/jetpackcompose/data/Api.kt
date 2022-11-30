package com.wishes.jetpackcompose.data


import com.example.wishes_jetpackcompose.data.entities.Ads
import com.example.wishes_jetpackcompose.data.entities.Apps
import com.example.wishes_jetpackcompose.data.entities.Categories
import com.example.wishes_jetpackcompose.data.entities.Images
import retrofit2.Response
import retrofit2.http.*


interface Api {

    @GET("ImgCats")
    suspend fun getImgesCategories(
        @Query("language") language: Int = 3
    ): Response<Categories>

    @GET("images")
    suspend fun getImages(
        @Query("language") language: Int = 3
    ): Response<Images>


    @Headers("Content-Type: application/json")
    @PUT("incShareImg")
    suspend fun incShareImg(
        @Query("id") id: Int?
    )

    @Headers("Content-Type: application/json")
    @PUT("incDownloadImg")
    suspend fun incDownloadImg(
        @Query("id") id: Int
    )

    @Headers("Content-Type: application/json")
    @PUT("incFavImg")
    suspend fun incFavImg(
        @Query("id") id: Int
    )

    @Headers("Content-Type: application/json")
    @PUT("incViewsImg")
    suspend fun incViewsImg(
        @Query("id") id: Int
    )

    //--------------------------------------------------------
    //quotes
    @GET("categoriesQuote")
    suspend fun getQuotesCategories(
        @Query("language") language: Int
    ): Response<Categories>


    @Headers("Content-Type: application/json")
    @PUT("incShareQuote")
    suspend fun incShareQuote(
        @Query("id") id: Int
    )


    @Headers("Content-Type: application/json")
    @PUT("incViewQuote")
    suspend fun incViewsQuote(
        @Query("id") id: Int
    )

    @Headers("Content-Type: application/json")
    @PUT("incFavQuote")
    suspend fun incFavQuote(
        @Query("id") id: Int
    )


    @Headers("Content-Type: application/json")
    @PUT("incrementStickerViews")
    suspend fun incrementStickerViews(
        @Query("id") id: Int = 7
    )

    @Headers("Content-Type: application/json")
    @PUT("incrementStickerAddTo")
    suspend fun incrementStickerAddTo(
        @Query("id") id: Int
    )

    @GET("apps")
    suspend fun getApps(): Response<Apps>
    //get Lnaguages


    @GET("ads")
    suspend fun getAds(): Response<Ads>

    @GET("admobe/{lg}")
    suspend fun getAdmobAds(
        @Path("lg") lg: Int
    ): Response<Ads>


    //ads
    @GET("ads/{package}")
    suspend fun getAds(
        @Path("package") packageName: String
    ): Response<Ads?>

}