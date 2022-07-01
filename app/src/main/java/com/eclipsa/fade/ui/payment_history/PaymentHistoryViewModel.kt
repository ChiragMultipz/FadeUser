package com.eclipsa.fade.ui.payment_history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.eclipsa.fade.network.Resource
import com.eclipsa.fade.ui.base.BaseViewModel
import com.eclipsa.fade.ui.fragment.booking_list.NewBookingListResponse
import kotlinx.coroutines.launch

class PaymentHistoryViewModel constructor(private val repository: PaymentHistoryRepository) : BaseViewModel(repository){

    private val _userPaymentHistoryResponse: MutableLiveData<Resource<UserPaymentHistoryResponse>> = MutableLiveData()
    val userPaymentHistoryResponse: LiveData<Resource<UserPaymentHistoryResponse>>
        get() = _userPaymentHistoryResponse

    suspend fun getUserPaymentHistory(
    ) = viewModelScope.launch {
        _userPaymentHistoryResponse.value = Resource.Loading
        _userPaymentHistoryResponse.value = repository.getUserPaymentHistory()
    }
}

