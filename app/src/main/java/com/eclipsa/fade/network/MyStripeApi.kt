package com.eclipsa.fade.network
import com.eclipsa.fade.ui.appointment.DriverSlotsResponse
import com.eclipsa.fade.ui.fragment.dashboard.DriversResponse
import com.eclipsa.fade.ui.fragment.dashboard.HomeOfferResponse
import com.eclipsa.fade.ui.payment_details.BookSlotsResponse
import com.eclipsa.fade.ui.payment_details.CardTokensResponse
import com.eclipsa.fade.ui.payment_details.PaymentKeyResponse
import com.eclipsa.fade.ui.profile.ProfileResponse
import com.eclipsa.fade.ui.select_service.ServicesResponse
import com.eclipsa.fade.utils.Constants
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface MyStripeApi {

    @FormUrlEncoded
    @POST("tokens")
    suspend fun getCardTokens(
        @FieldMap params: Map<String, String>,
        @Header("Authorization") headerAuth : String
    ): CardTokensResponse

    companion object {

        var myApi: MyStripeApi? = null

        fun getInstance(): MyStripeApi {
            if (myApi == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL_STRIPE)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                myApi = retrofit.create(MyStripeApi::class.java)
            }
            return myApi!!
        }


    }

}