package com.eclipsa.fade.ui.select_country

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


class SelectCountryViewModelFactory(private val repository: SelectCountryRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SelectCountryViewModel(repository) as T
    }
}