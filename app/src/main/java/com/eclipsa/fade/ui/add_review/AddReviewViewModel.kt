package com.eclipsa.fade.ui.add_review

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.eclipsa.fade.network.CommonResponse
import com.eclipsa.fade.network.Resource
import com.eclipsa.fade.ui.base.BaseViewModel
import kotlinx.coroutines.launch
import okhttp3.MultipartBody

class AddReviewViewModel constructor(private val repository: AddReviewRepository) :
    BaseViewModel(repository) {
    private val _addReviewResponse: MutableLiveData<Resource<CommonResponse>> = MutableLiveData()
    val addReviewResponse: LiveData<Resource<CommonResponse>>
        get() = _addReviewResponse

    suspend fun addReview(
        files: List<MultipartBody.Part>,
        params: Map<String,String>
    ) = viewModelScope.launch {
        _addReviewResponse.value = Resource.Loading
        _addReviewResponse.value = repository.addReview(files, params)
    }

    suspend fun addReviewWithoutImage(
        params: Map<String,String>
    ) = viewModelScope.launch {
        _addReviewResponse.value = Resource.Loading
        _addReviewResponse.value = repository.addReviewWithoutImage(params)
    }
}