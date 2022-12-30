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
    @IgnoredOnParcel
    var isSelected: Boolean = false
) : Parcelable {

    fun getLanguageName(): String {
        return name ?: "Unknown Language"
    }

    fun toggleSelection() {
        isSelected = !isSelected
    }

    companion object {
        fun createLanguageList(): List<LanguageApp> {
            val languages = arrayListOf<LanguageApp>()
            languages.add(LanguageApp(1, "English", "en", "english.json", "dispo1", "en"))
            languages.add(LanguageApp(2, "French", "fr", "french.json", "dispo2", "fr"))
            languages.add(LanguageApp(3, "Spanish", "es", "spanish.json", "dispo3", "es"))
            languages.add(LanguageApp(1, "English", "en", "english.json", "dispo1", "en"))
            languages.add(LanguageApp(2, "French", "fr", "french.json", "dispo2", "fr"))
            languages.add(LanguageApp(3, "Spanish", "es", "spanish.json", "dispo3", "es"))
            languages.add(LanguageApp(1, "English", "en", "english.json", "dispo1", "en"))
            languages.add(LanguageApp(2, "French", "fr", "french.json", "dispo2", "fr"))
            languages.add(LanguageApp(3, "Spanish", "es", "spanish.json", "dispo3", "es"))
            languages.add(LanguageApp(1, "English", "en", "english.json", "dispo1", "en"))
            languages.add(LanguageApp(2, "French", "fr", "french.json", "dispo2", "fr"))
            languages.add(LanguageApp(3, "Spanish", "es", "spanish.json", "dispo3", "es"))

            return languages
        }
    }
}

data class Languages(
    @SerializedName("Languages")
    val languages: List<LanguageApp>?
)

