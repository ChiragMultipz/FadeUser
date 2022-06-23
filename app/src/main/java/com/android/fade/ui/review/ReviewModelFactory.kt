package com.android.fade.ui.review

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.android.fade.ui.login.LoginRepository

class ReviewModelFactory(private val reviewRepository: ReviewRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ReviewListViewModel(reviewRepository) as T
    }

}
