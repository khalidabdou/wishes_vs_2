package com.example.wishes_jetpackcompose.data.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.wishes_jetpackcompose.utlis.Const.Companion.TABLE_CATEGORY
import com.google.gson.annotations.SerializedName

import kotlinx.android.parcel.Parcelize


@Parcelize
@Entity(tableName = TABLE_CATEGORY)
data class Category(

    @SerializedName("id", alternate = ["cid"])
    @PrimaryKey
    val id: Int,
    @SerializedName("category_name", alternate = ["name"])
    val name: String,
    @SerializedName("category_image", alternate = ["image"])
    val image: String,
    val color: String,
    var type: String?,
    val total_wallpaper: String?,
    val language_app: Int

) : Parcelable

data class Categories(
    @SerializedName("cats")
    val listCategory: List<Category>
)