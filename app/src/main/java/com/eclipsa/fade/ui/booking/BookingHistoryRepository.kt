package com.eclipsa.fade.ui.booking

import com.eclipsa.fade.network.MyApi
import com.eclipsa.fade.repository.BaseRepository

class BookingHistoryRepository constructor(private val api : MyApi) : BaseRepository() {

    suspend fun getUserBookings(
    ) = safeApiCall {
        api.getUserBookings()
    }
}