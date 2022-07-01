package com.eclipsa.fade.ui.code_verify

import com.eclipsa.fade.network.MyApi
import com.eclipsa.fade.repository.BaseRepository

class CodeVerifyRepository constructor(private val api : MyApi) : BaseRepository() {

    suspend fun login(
        mobile: String,
        phone_code: String
    ) = safeApiCall {
        api.login(mobile,phone_code)
    }

    suspend fun loginUserOtpVerify(
        email: String,
        otp: Int
    ) = safeApiCall {
        api.loginUserOtpVerify(email,otp)
    }


}
