package com.example.wishes_jetpackcompose.data.entities

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
 class Page(
    val page: Int,
    var imagesList: String,
    val cat:Int?
    ):Parcelable