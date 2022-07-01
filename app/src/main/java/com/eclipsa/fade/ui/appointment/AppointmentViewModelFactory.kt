package com.eclipsa.fade.ui.appointment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class AppointmentViewModelFactory(private val appointmentsRepository: AppointmentsRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AppointmentViewModel(appointmentsRepository) as T
    }
}
