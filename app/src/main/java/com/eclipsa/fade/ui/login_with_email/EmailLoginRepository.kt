package com.eclipsa.fade.ui.login_with_email

import com.eclipsa.fade.network.MyApi
import com.eclipsa.fade.repository.BaseRepository

class EmailLoginRepository constructor(private val api : MyApi) : BaseRepository() {

    suspend fun getCountryCode(
    ) = safeApiCall {
        api.getCountryCode()
    }

    suspend fun loginUser(
        email: String
    ) = safeApiCall {
        api.loginUser(email)
    }
}