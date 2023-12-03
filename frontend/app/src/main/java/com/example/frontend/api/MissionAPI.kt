package com.example.frontend.api

import com.example.frontend.model.AuthResponse
import com.example.frontend.model.EmailModel
import com.example.frontend.model.LoginModel
import com.example.frontend.model.MissionModel
import com.example.frontend.model.RegisterModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface
MissionAPI {
    @POST("/missions/{title}/mark_completed")
    fun markMissionCompleted(@Path("title") missionTitle: String): Call<Boolean>

    @GET("/missions")
    fun getMissionList() : Call<List<MissionModel>>


}
