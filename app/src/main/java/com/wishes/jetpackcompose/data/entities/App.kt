package com.wishes.jetpackcompose.data.entities

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


data class App(
    val id: Int,
    val title: String,
    val desc: String,
    val url: String,
    val image: String,
)


data class Apps(
    @SerializedName(value = "admobe", alternate = ["ads"])
    val ads: List<Ad>,

    @SerializedName("apps")
    val apps: List<App>
)