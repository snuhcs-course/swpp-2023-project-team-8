package com.example.frontend.api

import com.example.frontend.model.UserWithLocationModel
import com.example.frontend.utilities.BASE_URL
import com.example.frontend.utilities.GsonProvider
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface FriendService {

    @GET("/friends/nearby")
    fun getNearbyFriends(): Call<List<UserWithLocationModel>>
    companion object {
        fun create(): FriendService {
            val logger = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(GsonProvider.gson))
                .build()
                .create(FriendService::class.java)
        }
    }
}