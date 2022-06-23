package com.android.fade.ui.select_country

import com.android.fade.network.MyApi
import com.android.fade.repository.BaseRepository


class SelectCountryRepository constructor(private val api : MyApi) : BaseRepository() {

    suspend fun getCountryCode() = safeApiCall {
        api.getCountryCode()
    }

}