package com.android.fade.ui.barber_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class BarberDetailsViewModelFactory(private val repository: BarberDetailsRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return BarberDetailsViewModel(repository) as T
    }
}
