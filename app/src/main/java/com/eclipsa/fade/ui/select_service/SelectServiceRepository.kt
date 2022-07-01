package com.eclipsa.fade.ui.select_service

import com.eclipsa.fade.network.MyApi
import com.eclipsa.fade.repository.BaseRepository
import okhttp3.MultipartBody

class SelectServiceRepository (private val api : MyApi) : BaseRepository() {
    suspend fun getServices(
    ) = safeApiCall {
        api.getService()
    }
}