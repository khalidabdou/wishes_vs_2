package com.wishes.jetpackcompose.data.entities

import com.google.gson.annotations.SerializedName

data class Ad(
    var id: Int,
    var ad_id: String,
    var type: String,
    var ad_status: Boolean,
    var show_count: Int?,

    )

data class Setting(
    @SerializedName("package")
    val package_name: String,
    val dynamic_link: String,
    val email: String
)


data class Ads(
    @SerializedName(value = "admobe", alternate = ["ads"])
    val ads: List<Ad>,

    @SerializedName("apps")
    val apps: List<App>
)

class AdProvider {

    companion object {
        var Banner: Ad = Ad(
            0,
            "ca-app-pub-3940256099942544/6300978111-",
            "banner",
            false,
            null,
        )
        var Inter: Ad = Ad(
            0,
            "ca-app-pub-3940256099942544/1033173712-",
            "inter",
            false,
            3,
        )
        var OpenAd: Ad = Ad(
            0,
            "ca-app-pub-3940256099942544/3419835294-",
            "open",
            false,
            null,
        )
        var Rewarded: Ad = Ad(
            0,
            "",
            "rewarded",
            false,
            null,
        )


        var BannerFAN: Ad = Ad(
            0,
            "",
            "banner_fan",
            false,
            null,
        )
        var InterFAN: Ad = Ad(
            0,
            "",
            "inter_fan",
            false,
            10,
        )
    }
}