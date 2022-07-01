package com.eclipsa.fade.ui.review

import com.eclipsa.fade.network.MyApi
import com.eclipsa.fade.repository.BaseRepository
import okhttp3.MultipartBody

class ReviewRepository constructor(private val api : MyApi) : BaseRepository() {

    suspend fun getReview(
    ) = safeApiCall {
        api.getReview()
    }
}
