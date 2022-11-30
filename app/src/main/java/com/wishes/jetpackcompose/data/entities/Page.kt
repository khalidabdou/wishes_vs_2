package com.wishes.jetpackcompose.data.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
 class Page(
    val page: Int,
    var imagesList: String,
    val cat:Int?
    ):Parcelable