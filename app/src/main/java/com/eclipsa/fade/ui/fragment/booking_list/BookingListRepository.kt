package com.eclipsa.fade.ui.fragment.booking_list

import com.eclipsa.fade.network.MyApi
import com.eclipsa.fade.repository.BaseRepository

class BookingListRepository constructor(private val api : MyApi): BaseRepository(){

    suspend fun getUserBookings(
    ) = safeApiCall {
        api.getUserBookings()
    }

    suspend fun orderCancelByUser(
        params:Map<String, String>
    ) = safeApiCall {
        api.orderCancelByUser(params)
    }

}
