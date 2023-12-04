package com.example.frontend.api

import com.example.frontend.model.MeetupModel
import com.example.frontend.model.UserWithLocationModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface MeetUpService {
    @GET("/meet_ups")
    fun getMeetUps(): Call<List<MeetupModel>>
    @GET("/meet_ups/{id}")
    fun getMeetUpById(@Path("id") meetUpId: Int): Call<MeetupModel>
    @POST("/meet_ups")
    fun createMeetUp(@Body meetUp: MeetupModel): Call<MeetupModel>
}