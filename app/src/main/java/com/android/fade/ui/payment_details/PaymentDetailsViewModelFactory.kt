package com.android.fade.ui.payment_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class PaymentDetailsViewModelFactory(private val paymentDetailsRepository: PaymentDetailsRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PaymentDetailsViewModel(paymentDetailsRepository) as T
    }
}