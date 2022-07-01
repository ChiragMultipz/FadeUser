package com.eclipsa.fade.ui.booking

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class BookingHistoryViewModelFactory(private val bookingHistoryRepository: BookingHistoryRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return BookingHistoryViewModel(bookingHistoryRepository) as T
    }

}