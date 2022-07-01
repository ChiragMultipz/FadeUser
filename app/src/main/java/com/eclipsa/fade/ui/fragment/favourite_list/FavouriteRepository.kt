package com.eclipsa.fade.ui.fragment.favourite_list

import com.eclipsa.fade.network.MyApi
import com.eclipsa.fade.repository.BaseRepository

class FavouriteRepository constructor(private val api : MyApi): BaseRepository() {

    suspend fun getFavouriteBarber(
    ) = safeApiCall {
        api.getFavouriteBarbers()
    }

    suspend fun makeBarberFavUnFav(
        params: Map<String,String>
    ) = safeApiCall {
        api.makeBarberFavUnFav(params)
    }

}