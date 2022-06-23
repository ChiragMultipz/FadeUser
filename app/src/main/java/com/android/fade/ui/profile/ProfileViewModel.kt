package com.android.fade.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.android.fade.network.Resource
import com.android.fade.ui.base.BaseViewModel
import kotlinx.coroutines.launch
import okhttp3.MultipartBody

class ProfileViewModel constructor(private val repository: ProfileRepository) : BaseViewModel(repository){

    private val _updateProfileResponse: MutableLiveData<Resource<ProfileResponse>> = MutableLiveData()
    val updateProfileResponse: LiveData<Resource<ProfileResponse>>get() = _updateProfileResponse

    suspend fun addProfile(
        files: List<MultipartBody.Part>,
        params: Map<String,String>
    ) = viewModelScope.launch {
        _updateProfileResponse.value = Resource.Loading
        _updateProfileResponse.value = repository.addProfile(files, params)
    }

    suspend fun updateProfile(
        files: List<MultipartBody.Part>,
        params: Map<String,String>
    ) = viewModelScope.launch {
        _updateProfileResponse.value = Resource.Loading
        _updateProfileResponse.value = repository.updateProfileWithPhoto(files,params)
    }

    suspend fun updateProfileWithoutPhoto(
        params: Map<String,String>
    ) = viewModelScope.launch {
        _updateProfileResponse.value = Resource.Loading
        _updateProfileResponse.value = repository.updateProfileWithoutPhoto(params)
    }

    private val _getCityResponse: MutableLiveData<Resource<CityResponse>> = MutableLiveData()
    val getCityResponse: LiveData<Resource<CityResponse>>
        get() = _getCityResponse

    private val _getStateResponse: MutableLiveData<Resource<StateResponse>> = MutableLiveData()
    val getStateResponse: LiveData<Resource<StateResponse>>
        get() = _getStateResponse

    suspend fun getStates(
        countryId:String
    ) = viewModelScope.launch {
        _getStateResponse.value = Resource.Loading
        _getStateResponse.value = repository.getStates(countryId)
    }

    suspend fun getCity(
    ) = viewModelScope.launch {
        _getCityResponse.value = Resource.Loading
        _getCityResponse.value = repository.getCities()
    }
}

