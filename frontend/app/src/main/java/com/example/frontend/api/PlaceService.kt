package com.example.frontend.api

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.frontend.interceptor.AuthInterceptor
import com.example.frontend.model.PlaceModel
import com.example.frontend.model.PlaceResponse
import com.example.frontend.model.PlaceResponseWrapper
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

    @GET("places/recommend")
    fun recommend(@Query("user_ids[]") friendIds: List<Long>): Call<PlaceResponseWrapper>

    companion object {
        @RequiresApi(Build.VERSION_CODES.O)
        fun create(context: Context): PlaceService {
            val logger =
                HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }

            val client = OkHttpClient.Builder()
                .addInterceptor(AuthInterceptor(context))
                //    .addInterceptor(logger)
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
