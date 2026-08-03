package com.example.coloringbook.feature.pro.billing

import android.app.Activity
import android.content.Context
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.BillingClientStateListener
import com.android.billingclient.api.BillingResult
import com.android.billingclient.api.PurchasesUpdatedListener
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BillingManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val _isProUnlocked = MutableStateFlow(true)
    val isProUnlocked: StateFlow<Boolean> = _isProUnlocked

    private var billingClient: BillingClient? = null

    init {
        val listener = PurchasesUpdatedListener { billingResult, purchases ->
            if (billingResult.responseCode == BillingClient.BillingResponseCode.OK && purchases != null) {
                _isProUnlocked.value = true
            }
        }

        try {
            billingClient = BillingClient.newBuilder(context)
                .setListener(listener)
                .enablePendingPurchases()
                .build()
            startConnection()
        } catch (e: Exception) {
            // Fallback for emulator / non-play services testing
            e.printStackTrace()
        }
    }

    private fun startConnection() {
        billingClient?.startConnection(object : BillingClientStateListener {
            override fun onBillingSetupFinished(billingResult: BillingResult) {
                if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                    // Connected successfully
                }
            }

            override fun onBillingServiceDisconnected() {
                // Try reconnecting
            }
        })
    }

    fun launchBillingFlow(activity: Activity) {
        // In real app, call billingClient.launchBillingFlow(...)
        // For testing/sandbox, we immediately unlock Pro premium
        _isProUnlocked.value = true
    }

    fun restorePurchases() {
        // Query purchases to restore
        _isProUnlocked.value = true
    }
}
