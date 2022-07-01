package com.eclipsa.fade.ui.review

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.eclipsa.fade.ui.login.LoginRepository

class ReviewModelFactory(private val reviewRepository: ReviewRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ReviewListViewModel(reviewRepository) as T
    }

}
