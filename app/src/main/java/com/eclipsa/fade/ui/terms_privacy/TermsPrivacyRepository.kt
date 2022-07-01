package com.eclipsa.fade.ui.terms_privacy

import com.eclipsa.fade.network.MyApi
import com.eclipsa.fade.repository.BaseRepository

class TermsPrivacyRepository(private val api: MyApi) : BaseRepository() {

    // 3 = User
    suspend fun getTermsPolicy(
    ) = safeApiCall {
        api.getTermsPolicy("3")
    }

}