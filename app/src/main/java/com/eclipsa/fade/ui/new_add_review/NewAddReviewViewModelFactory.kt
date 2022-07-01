package com.eclipsa.fade.ui.new_add_review

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class NewAddReviewViewModelFactory(private val addReviewRepository: NewAddReviewRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NewAddReviewViewModel(addReviewRepository) as T
    }

}