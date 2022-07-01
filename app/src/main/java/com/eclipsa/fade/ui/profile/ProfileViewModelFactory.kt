package com.eclipsa.fade.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.eclipsa.fade.ui.login.LoginRepository

class ProfileViewModelFactory(private val profileRepository: ProfileRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ProfileViewModel(profileRepository) as T
    }

}
