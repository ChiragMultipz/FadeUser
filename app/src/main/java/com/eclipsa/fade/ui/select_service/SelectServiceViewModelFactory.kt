package com.eclipsa.fade.ui.select_service

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class SelectServiceViewModelFactory(private val selectServiceRepository: SelectServiceRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SelectServiceViewModel(selectServiceRepository) as T
    }
}
