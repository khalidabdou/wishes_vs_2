package com.wishes.jetpackcompose.utlis

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.wishes.jetpackcompose.BuildConfig
import com.wishes.jetpackcompose.admob.applovin

class Const {

    companion object {

        const val DATABASE_NAME = "MY_DB"
        const val TABLE_IMAGE = "tbl_images"
        const val TABLE_CATEGORY = "tbl_category"
        const val TABLE_WALLPAPER = "tbl_wallpaper"
        const val LANGUAGE_PREFERENCE="language"

        val directoryUpload: String = BuildConfig.api + "categories/"
        val directoryUploadCat: String = BuildConfig.api + "category/"

        var LANGUAGE_ID: Int = 7
        var RANDOM = (0..10000).random()

        val applovinClass = applovin()


        fun hasConnection(application: Application): Boolean {
            val connectivityManager = application.getSystemService(
                Context.CONNECTIVITY_SERVICE
            ) as ConnectivityManager
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val capabilites =
                connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
            return when {
                capabilites.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                capabilites.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                capabilites.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        }
    }
}