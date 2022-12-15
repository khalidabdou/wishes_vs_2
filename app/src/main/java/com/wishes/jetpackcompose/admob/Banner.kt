package com.ringtones.compose.feature.admob

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.wishes.jetpackcompose.data.entities.AdProvider.Companion.Banner
import com.wishes.jetpackcompose.data.entities.AdProvider.Companion.BannerFAN


@Composable
fun AdvertViewAdmob(modifier: Modifier = Modifier) {
    val isInEditMode = LocalInspectionMode.current
    if (isInEditMode) {
        Text(
            modifier = modifier
                .fillMaxSize()
                .background(Red)
                .padding(horizontal = 2.dp, vertical = 2.dp),
            textAlign = TextAlign.Center,
            color = White,
            text = "",
        )
    } else {
        AndroidView(
            modifier = modifier
                .fillMaxWidth()
                .height(AdSize.BANNER.height.dp + 4.dp)
                .background(MaterialTheme.colorScheme.surface),
            factory = { context ->
                AdView(context).apply {
                    setAdSize(AdSize.BANNER)
                    adUnitId = Banner.ad_id
                    loadAd(AdRequest.Builder().build())
                }
            }
        )
    }
}


@Composable
fun AdvertViewFAN(modifier: Modifier = Modifier) {
        Log.d("FAN", BannerFAN.ad_id)
        AndroidView(
            modifier = modifier
                .fillMaxWidth()
                .height(AdSize.BANNER.height.dp + 4.dp)
                .background(MaterialTheme.colorScheme.surface),
            factory = { context ->
                com.facebook.ads.AdView(
                    context,
                    BannerFAN.ad_id,
                    com.facebook.ads.AdSize.BANNER_HEIGHT_50
                ).apply {
                    loadAd()
                }
            }
        )

}
