package com.kimikevin.prepify

import android.app.Application
import com.revenuecat.purchases.LogLevel
import com.revenuecat.purchases.Purchases
import com.revenuecat.purchases.PurchasesConfiguration

class PrepifyApp : Application() {
    override fun onCreate() {
        super.onCreate()

        // Initialize RevenueCat SDK
        Purchases.configure(
            PurchasesConfiguration.Builder(this, BuildConfig.REVENUECAT_API_KEY)
                .build()
        )
        
        // Best practice: Enable debug logs in debug mode
        if (BuildConfig.DEBUG) {
            Purchases.logLevel = LogLevel.DEBUG
        }
    }
}
