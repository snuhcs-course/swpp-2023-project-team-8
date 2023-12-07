package com.example.frontend.api

import android.content.Context
import com.example.frontend.interceptor.AuthInterceptor

import com.example.frontend.model.MeetUpResponse
import com.example.frontend.model.MeetupModel
import com.example.frontend.utilities.BASE_URL
import com.example.frontend.utilities.GsonProvider
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface MeetUpService {
    @GET("/meet_ups")
    fun getMeetUps(): Call<List<MeetUpResponse>>

    @POST("/meet_ups")
    fun createMeetUp(@Body meetUp: MeetupModel): Call<Void>

    companion object {
        fun create(context: Context): MeetUpService {

            val logger = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }

            val client = OkHttpClient.Builder()
                .addInterceptor(AuthInterceptor(context))
                .addInterceptor(logger)
                .addInterceptor(AuthInterceptor(context))
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(GsonProvider.gson))
                .build()
                .create(MeetUpService::class.java)
        }
    }
}