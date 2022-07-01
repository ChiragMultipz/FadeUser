package com.eclipsa.fade.ui.notification

import com.eclipsa.fade.network.MyApi
import com.eclipsa.fade.network.MyStripeApi
import com.eclipsa.fade.repository.BaseRepository
import java.util.HashMap

class NotificationListRepository(private val api: MyApi) : BaseRepository() {

    suspend fun getNotification(
    ) = safeApiCall {
        api.getNotification()
    }

    suspend fun readNotification(
    ) = safeApiCall {
        api.readNotification()
    }

}