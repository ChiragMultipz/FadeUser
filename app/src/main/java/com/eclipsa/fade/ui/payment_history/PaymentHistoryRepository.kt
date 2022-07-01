package com.eclipsa.fade.ui.payment_history

import com.eclipsa.fade.network.MyApi
import com.eclipsa.fade.repository.BaseRepository
import okhttp3.MultipartBody

class PaymentHistoryRepository constructor(private val api : MyApi) : BaseRepository() {

    suspend fun getUserPaymentHistory(
    ) = safeApiCall {
        api.getUserPaymentHistory()
    }
}
