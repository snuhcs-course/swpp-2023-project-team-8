package com.example.frontend.api

import android.content.Context
import com.example.frontend.interceptor.AuthInterceptor
<<<<<<< HEAD
=======
import com.example.frontend.model.MeetUpResponse
>>>>>>> 7bd50ee32ea221baa2088386125f02a9aeae52d2
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
<<<<<<< HEAD
    fun getMeetUps(): Call<List<MeetupModel>>
=======
    fun getMeetUps(): Call<List<MeetUpResponse>>

    @GET("/meet_ups/{id}")
    fun getMeetUpById(@Path("id") meetUpId: Int): Call<MeetupModel>
>>>>>>> 7bd50ee32ea221baa2088386125f02a9aeae52d2

    @POST("/meet_ups")
    fun createMeetUp(@Body meetUp: MeetupModel): Call<Void>

    companion object {
        fun create(context: Context): MeetUpService {
<<<<<<< HEAD
            val logger = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }
=======
            val logger =
                HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }
>>>>>>> 7bd50ee32ea221baa2088386125f02a9aeae52d2

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