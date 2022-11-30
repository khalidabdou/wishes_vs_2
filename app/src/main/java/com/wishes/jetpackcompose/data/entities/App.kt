package com.wishes.jetpackcompose.data.entities

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
data class App(
    val id: Int,
    val name: String,
    val description: String,
    val app_url: String,
    val icon: String,
    val larg_image: String,
    val btn_text: String
) : Parcelable


data class Apps(
    @SerializedName("apps")
    val results: List<App>
)