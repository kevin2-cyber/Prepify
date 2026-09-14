package com.kimikevin.prepify

import com.revenuecat.purchases.CustomerInfo
import com.revenuecat.purchases.Offerings
import com.revenuecat.purchases.Purchases
import com.revenuecat.purchases.PurchasesError
import com.revenuecat.purchases.getCustomerInfoWith
import com.revenuecat.purchases.getOfferingsWith

object PurchasesManager {
    private const val PRO_ENTITLEMENT_ID = "prepify_pro"

    /**
     * Checks if the user has the 'prepify_pro' entitlement.
     */
    fun checkProEntitlement(onResult: (Boolean) -> Unit) {
        Purchases.sharedInstance.getCustomerInfoWith(
            onError = { onResult(false) },
            onSuccess = { customerInfo ->
                val isPro = customerInfo.entitlements[PRO_ENTITLEMENT_ID]?.isActive == true
                onResult(isPro)
            }
        )
    }

    /**
     * Fetches the current customer info.
     */
    fun getCustomerInfo(onResult: (CustomerInfo?, PurchasesError?) -> Unit) {
        Purchases.sharedInstance.getCustomerInfoWith(
            onError = { error -> onResult(null, error) },
            onSuccess = { customerInfo -> onResult(customerInfo, null) }
        )
    }

    /**
     * Fetches offerings (products) configured in RevenueCat.
     */
    fun fetchOfferings(onResult: (Offerings?, PurchasesError?) -> Unit) {
        Purchases.sharedInstance.getOfferingsWith(
            onError = { error -> onResult(null, error) },
            onSuccess = { offerings -> onResult(offerings, null) }
        )
    }
}
