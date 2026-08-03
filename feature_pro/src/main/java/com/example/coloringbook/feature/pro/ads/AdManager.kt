package com.example.coloringbook.feature.pro.ads

import android.app.Activity
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.gms.ads.MobileAds
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AdManager @Inject constructor() {

    fun initAdMob(context: Context) {
        try {
            MobileAds.initialize(context) {}
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun showInterstitial(activity: Activity, onAdClosed: () -> Unit) {
        // In real app, load and show interstitial.
        // For sandbox/testing we immediately call the callback.
        onAdClosed()
    }
}

@Composable
fun AdMobBannerView(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(Color.LightGray.copy(alpha = 0.3f)),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "AdMob Banner Ad", fontSize = 12.sp, color = Color.Gray)
    }
}
