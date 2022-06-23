package com.android.fade.ui.add_review

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class AddReviewViewModelFactory(private val addReviewRepository: AddReviewRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AddReviewViewModel(addReviewRepository) as T
    }

}