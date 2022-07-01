package com.eclipsa.fade.ui.select_country

import com.eclipsa.fade.network.MyApi
import com.eclipsa.fade.repository.BaseRepository


class SelectCountryRepository constructor(private val api : MyApi) : BaseRepository() {

    suspend fun getCountryCode() = safeApiCall {
        api.getCountryCode()
    }

}