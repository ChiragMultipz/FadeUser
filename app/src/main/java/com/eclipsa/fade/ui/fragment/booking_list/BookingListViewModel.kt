package com.eclipsa.fade.ui.fragment.booking_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.eclipsa.fade.network.CommonResponse
import com.eclipsa.fade.network.Resource
import com.eclipsa.fade.ui.base.BaseViewModel
import com.eclipsa.fade.ui.fragment.profile.ProfileFragmentRepository
import com.eclipsa.fade.ui.select_service.ServicesResponse
import kotlinx.coroutines.launch

class BookingListViewModel (val repository: BookingListRepository) : BaseViewModel(repository)  {

    private val _newOrderResponse: MutableLiveData<Resource<NewBookingListResponse>> = MutableLiveData()
    val newOrderResponse: LiveData<Resource<NewBookingListResponse>>
        get() = _newOrderResponse

    private val _cancelOrderResponse: MutableLiveData<Resource<CommonResponse>> = MutableLiveData()
    val cancelOrderResponse: LiveData<Resource<CommonResponse>>
        get() = _cancelOrderResponse

    suspend fun getUserBookings(
    ) = viewModelScope.launch {
        _newOrderResponse.value = Resource.Loading
        _newOrderResponse.value = repository.getUserBookings()
    }

    suspend fun orderCancelByUser(
        params : Map<String, String>
    ) = viewModelScope.launch {
        _cancelOrderResponse.value = Resource.Loading
        _cancelOrderResponse.value = repository.orderCancelByUser(params)
    }
}