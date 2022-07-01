package com.eclipsa.fade.ui.code_verify

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.eclipsa.fade.network.Resource
import com.eclipsa.fade.ui.base.BaseViewModel
import com.eclipsa.fade.ui.profile.ProfileResponse
import kotlinx.coroutines.launch

class CodeVerifyViewModel constructor(private val repository: CodeVerifyRepository) :
    BaseViewModel(repository) {

    private val _loginResponse: MutableLiveData<Resource<ProfileResponse>> = MutableLiveData()
    val loginResponse: LiveData<Resource<ProfileResponse>>
        get() = _loginResponse

    private val _emailLoginResponse: MutableLiveData<Resource<ProfileResponse>> = MutableLiveData()
    val emailLoginResponse: LiveData<Resource<ProfileResponse>>
        get() = _emailLoginResponse

    suspend fun login(
        mobile: String,
        phone_code: String
    ) = viewModelScope.launch {
        _loginResponse.value = Resource.Loading
        _loginResponse.value = repository.login(mobile,phone_code)
    }

    suspend fun loginUserOtpVerify(
        email: String,
        otp: Int
    ) = viewModelScope.launch {
        _emailLoginResponse.value = Resource.Loading
        _emailLoginResponse.value = repository.loginUserOtpVerify(email,otp)
    }



}