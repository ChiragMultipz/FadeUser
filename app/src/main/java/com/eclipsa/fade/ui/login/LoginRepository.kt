package com.eclipsa.fade.ui.login

import com.eclipsa.fade.network.MyApi
import com.eclipsa.fade.repository.BaseRepository

class LoginRepository constructor(private val api : MyApi) : BaseRepository() {

    suspend fun getCountryCode(
    ) = safeApiCall {
        api.getCountryCode()
    }
}