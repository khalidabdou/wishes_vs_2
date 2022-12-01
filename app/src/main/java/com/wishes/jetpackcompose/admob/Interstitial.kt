package com.wishes.jetpackcompose.admob

import android.content.Context
import android.util.Log


import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.wishes.jetpackcompose.data.entities.AdProvider.Companion.Inter
import com.wishes.jetpackcompose.utlis.findActivity


var mInterstitialAd: InterstitialAd? = null
var countShow = -1
val showAd = 10

// load the interstitial ad
fun loadInterstitial(context: Context) {



    if (!Inter.ad_status)
        return

    InterstitialAd.load(
        context,
        Inter.ad_id,
        AdRequest.Builder().build(),
        object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                mInterstitialAd = null
                Log.d("MainActivity", adError.message)
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                mInterstitialAd = interstitialAd
                Log.d("MainActivity", "Ad was loaded.")
            }
        }

    )
}


// show the interstitial ad
fun showInterstitial(context: Context) {

    if (mInterstitialAd != null) {

        val activity = context.findActivity()
        mInterstitialAd?.fullScreenContentCallback =
            object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    Log.d("MainActivity", "Ad was dismissed.")
                    // Don't forget to set the ad reference to null so you
                    // don't show the ad a second time.
                    mInterstitialAd = null
                    loadInterstitial(activity!!)
                }

                override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                    Log.d("MainActivity", "Ad failed to show.")
                    // Don't forget to set the ad reference to null so you
                    // don't show the ad a second time.
                    mInterstitialAd = null
                }

                override fun onAdShowedFullScreenContent() {
                    Log.d("MainActivity", "Ad showed fullscreen content.")
                    // Called when ad is dismissed.
                }
            }
        mInterstitialAd?.show(activity!!)


    } else {
        loadInterstitial(context)
        Log.d("MainActivity", "The interstitial ad wasn't ready yet.")
    }
}

fun showInterstitialAfterClick(context: Context) {

    if (!Inter.ad_status)
        return
    countShow++
    if (mInterstitialAd != null) {
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
                }

                override fun onAdShowedFullScreenContent() {
                    Log.d("MainActivity", "Ad showed fullscreen content.")
                    // Called when ad is dismissed.
                }
            }
        mInterstitialAd?.show(activity!!)


    } else {
        loadInterstitial(context)
        Log.d("MainActivity", "The interstitial ad wasn't ready yet.")
    }
}