package com.wishes.jetpackcompose.data.entities

import android.os.Parcelable
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import kotlinx.parcelize.IgnoredOnParcel


@Parcelize
data class LanguageApp(
    @SerializedName("id")
    @PrimaryKey
    var Id: Int,
    val name: String?,
    var label: String,
    val filename: String?,
    val dispo: String?,
    val language_code : String,
) : Parcelable{
    @IgnoredOnParcel
    var isSelected: Boolean = false
}

data class Languages(
    @SerializedName("Languages")
    val languages: List<LanguageApp>
)

