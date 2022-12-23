package com.wishes.jetpackcompose.admob


import android.app.Activity
import android.content.Context
import android.util.Log
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.wishes.jetpackcompose.data.entities.AdProvider.Companion.Inter
import com.wishes.jetpackcompose.data.entities.AdProvider.Companion.InterApplovin
import com.wishes.jetpackcompose.data.entities.AdProvider.Companion.InterFAN
import com.wishes.jetpackcompose.utlis.findActivity


var mInterstitialAd: InterstitialAd? = null
var countShow = -1
val showAd = 10
var applovin = applovin()

// load the interstitial ad
fun loadInterstitial(context: Context) {
    InterstitialAd.load(
        context,
        Inter.ad_id,
        AdRequest.Builder().build(),
        object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                mInterstitialAd = null
                Log.d("MainActivity", adError.message)
                Inter.ad_status = false
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                mInterstitialAd = interstitialAd
                Log.d("MainActivity", "Ad was loaded.")
            }
        }

    )
}


fun showInterstitialAfterClick(context: Context) {

    countShow++
    //Log.d("MainActivity", "$countShow")

    if (Inter.ad_status) {
        //Log.d("MainActivity", "Ad admob.")
        if (mInterstitialAd == null) {
            loadInterstitial(context)
        }
        if (countShow % Inter.show_count!! != 0) {
            return
        }

        val activity = context.findActivity()
        mInterstitialAd?.fullScreenContentCallback =
            object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    //Log.d("MainActivity", "Ad was dismissed.")
                    mInterstitialAd = null
                    loadInterstitial(activity!!)
                }

                override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                    //Log.d("MainActivity", "Ad failed to show.")
                    mInterstitialAd = null
                    Facebook.showInterstitial(context as Activity)
                }

                override fun onAdShowedFullScreenContent() {
                }
            }
        mInterstitialAd?.show(activity!!)

    } else if (InterFAN.ad_status) {
        //Log.d("MainActivity", "Ad fan.")
        Facebook.showInterstitial(context as Activity)
    } else if (InterApplovin.ad_status) {

        applovin.createInterstitialAd(context)

    }
}

