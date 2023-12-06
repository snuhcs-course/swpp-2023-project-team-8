package com.example.frontend.api

import com.example.frontend.model.CheckInModel
import com.example.frontend.utilities.BASE_URL
import com.example.frontend.utilities.GsonProvider
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Used to connect to the server
 * About check in domain
 */
interface CheckInService {
    @POST("/check_ins")
    fun login(@Body checkInModel: CheckInModel): Call<CheckInModel?>?

    companion object {
        fun create(): CheckInService {
            val logger = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(GsonProvider.gson))
                .build()
                .create(CheckInService::class.java)
        }
    }
}
