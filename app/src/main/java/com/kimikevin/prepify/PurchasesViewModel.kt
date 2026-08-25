package com.kimikevin.prepify

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.revenuecat.purchases.CustomerInfo
import com.revenuecat.purchases.Purchases
import com.revenuecat.purchases.PurchasesError
import com.revenuecat.purchases.interfaces.UpdatedCustomerInfoListener
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PurchasesViewModel : ViewModel() {

    private val _isPro = MutableStateFlow(false)
    val isPro: StateFlow<Boolean> = _isPro

    private val _customerInfo = MutableStateFlow<CustomerInfo?>(null)
    val customerInfo: StateFlow<CustomerInfo?> = _customerInfo

    private val _error = MutableStateFlow<PurchasesError?>(null)
    val error: StateFlow<PurchasesError?> = _error

    init {
        // Set up listener for customer info updates
        Purchases.sharedInstance.updatedCustomerInfoListener = UpdatedCustomerInfoListener { customerInfo ->
            _customerInfo.value = customerInfo
            updateProStatus(customerInfo)
        }

        // Initial check
        refreshCustomerInfo()
    }

    fun refreshCustomerInfo() {
        PurchasesManager.getCustomerInfo { info, err ->
            if (info != null) {
                _customerInfo.value = info
                updateProStatus(info)
            } else {
                _error.value = err
            }
        }
    }

    private fun updateProStatus(info: CustomerInfo) {
        _isPro.value = info.entitlements["prepify_pro"]?.isActive == true
    }
}
