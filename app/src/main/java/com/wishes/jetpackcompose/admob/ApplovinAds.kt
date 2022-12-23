package com.wishes.jetpackcompose.admob

import android.app.Activity
import android.content.Context
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.FrameLayout
import com.applovin.mediation.MaxAd
import com.applovin.mediation.MaxAdListener
import com.applovin.mediation.MaxAdViewAdListener
import com.applovin.mediation.MaxError
import com.applovin.mediation.ads.MaxAdView
import com.applovin.mediation.ads.MaxInterstitialAd
import com.applovin.mediation.nativeAds.MaxNativeAdListener
import com.applovin.mediation.nativeAds.MaxNativeAdLoader
import com.applovin.mediation.nativeAds.MaxNativeAdView
import com.wishes.jetpackcompose.R


import java.util.concurrent.TimeUnit

class ApplovinAds : MaxAdViewAdListener {


    var context: Context? = null
    var adContainer: FrameLayout? = null

    constructor(context: Context, container: FrameLayout) {
        this.context = context
        this.adContainer = container
    }

    private var adView: MaxAdView? = null

    fun createBannerAd():MaxAdView? {
        adView = MaxAdView(context!!.getString(R.string.applovine_banner), context)
        adView?.setListener(this)
        //val width = ViewGroup.LayoutParams.MATCH_PARENT
        //val heightPx = context!!.resources.getDimensionPixelSize(R.dimen.banner_height)
        //adView?.layoutParams = FrameLayout.LayoutParams(width, heightPx)
        //adContainer!!.addView(adView)
        adView?.loadAd()
        return MaxAdView

    }

    override fun onAdLoaded(ad: MaxAd?) {
        adContainer!!.visibility = VISIBLE
    }

    override fun onAdDisplayed(ad: MaxAd?) {
        Log.d("applovinads", "onAdDisplayed")
    }

    override fun onAdHidden(ad: MaxAd?) {
        Log.d("applovinads", "onAdDisplayed")
    }

    override fun onAdClicked(ad: MaxAd?) {
        Log.d("applovinads", "onAdDisplayed")
    }

    override fun onAdLoadFailed(adUnitId: String?, error: MaxError?) {
        adContainer!!.visibility = GONE
        Log.d("applovinads", error.toString())
    }

    override fun onAdDisplayFailed(ad: MaxAd?, error: MaxError?) {
        Log.d("applovinads", "onAdDisplayed")
    }

    override fun onAdExpanded(ad: MaxAd?) {
        Log.d("applovinads", "onAdDisplayed")
    }

    override fun onAdCollapsed(ad: MaxAd?) {
        Log.d("applovinads", "onAdDisplayed")
    }
}


class ApplovinAdsInter : MaxAdListener {
    private lateinit var adContainerView: FrameLayout
    lateinit var interstitialAd: MaxInterstitialAd
    private var retryAttempt = 0.0
    private var context: Activity

    constructor(context: Activity) {
        this.context = context
    }


    fun createInterstitialAd() {
        interstitialAd =
            MaxInterstitialAd(context.getString(R.string.applovine_interstitial), context)
        interstitialAd.setExtraParameter("container_view_ads", "true")
        interstitialAd.setListener(this)

        Log.d("applovinads", "kkkkk")
        // Load the first ad

        interstitialAd.loadAd()
    }

    fun show() {
        if (interstitialAd.isReady) {
            interstitialAd.showAd()
        }

    }

    // MAX Ad Listener
    override fun onAdLoaded(maxAd: MaxAd) {
    }

    override fun onAdLoadFailed(adUnitId: String?, error: MaxError?) {
        // Interstitial ad failed to load
        // AppLovin recommends that you retry with exponentially higher delays up to a maximum delay (in this case 64 seconds)

        retryAttempt++
        val delayMillis =
            TimeUnit.SECONDS.toMillis(Math.pow(2.0, Math.min(6.0, retryAttempt)).toLong())

        Handler().postDelayed({ interstitialAd.loadAd() }, delayMillis)
    }

    override fun onAdDisplayFailed(ad: MaxAd?, error: MaxError?) {
        // Interstitial ad failed to display. AppLovin recommends that you load the next ad.
        interstitialAd.loadAd()
    }

    override fun onAdDisplayed(maxAd: MaxAd) {}

    override fun onAdClicked(maxAd: MaxAd) {}

    override fun onAdHidden(maxAd: MaxAd) {
        // Interstitial ad is hidden. Pre-load the next ad
        interstitialAd.loadAd()
    }


}


class ApplovinAdsNative : Activity() {
    private lateinit var nativeAdLoader: MaxNativeAdLoader
    private var nativeAd: MaxAd? = null

    fun createNativeAd(nativeAdContainer: FrameLayout) {
        nativeAdLoader = MaxNativeAdLoader(getString(R.string.applovine_native), this)
        nativeAdLoader.setNativeAdListener(object : MaxNativeAdListener() {

            override fun onNativeAdLoaded(nativeAdView: MaxNativeAdView?, ad: MaxAd) {
                // Clean up any pre-existing native ad to prevent memory leaks.
                if (nativeAd != null) {
                    nativeAdLoader.destroy(nativeAd)
                }

                // Save ad for cleanup.
                nativeAd = ad

                // Add ad view to view.
                nativeAdContainer.removeAllViews()
                nativeAdContainer.addView(nativeAdView)
            }

            override fun onNativeAdLoadFailed(adUnitId: String, error: MaxError) {
                // We recommend retrying with exponentially higher delays up to a maximum delay
            }

            override fun onNativeAdClicked(ad: MaxAd) {
                // Optional click callback
            }
        })
        nativeAdLoader.loadAd()
    }
}