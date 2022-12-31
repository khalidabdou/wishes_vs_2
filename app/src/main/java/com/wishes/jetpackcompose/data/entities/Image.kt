package com.wishes.jetpackcompose.data.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.wishes.jetpackcompose.utlis.Const.Companion.TABLE_IMAGE

import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = TABLE_IMAGE)
data class Image(
    @PrimaryKey
    var id: Int,
    val image_upload: String?,
    val cat_id: Int,
    val count_view: Int,
    val download_count: Int,
    val count_shared: Int,
    var isfav: Int?,
    val count_fav: Int?,
    val language_app: Int,
    @SerializedName("language")
    val languageLable: String

) : Parcelable

data class Images(
    @SerializedName("latest")
    var results: List<Image>
)