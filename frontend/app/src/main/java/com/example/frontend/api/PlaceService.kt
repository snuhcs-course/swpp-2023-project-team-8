package com.example.frontend.api

import com.example.frontend.model.PlaceModel
import com.example.frontend.utilities.BASE_URL
import com.example.frontend.utilities.GsonProvider
import com.google.android.gms.maps.model.LatLng
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query


interface PlaceService {
    @POST("/sendlocation")
    fun send(@Body placeModel: PlaceModel?): Call<PlaceModel>?

    @GET("/recommend")
    fun recommend(@Query("averagedLocation") averagedLocation: LatLng?): Call<List<PlaceModel>>
    companion object {
        fun create(): PlaceService {
            val logger = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(GsonProvider.gson))
                .build()
                .create(PlaceService::class.java)
        }
    }
}
