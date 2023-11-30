package com.example.frontend.api

import com.example.frontend.model.AuthResponse
import com.example.frontend.model.EmailModel
import com.example.frontend.model.LoginModel
import com.example.frontend.model.RegisterModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface
MissionAPI {
    @POST("/complete")
    fun completed(@Body confirmed: Boolean): Boolean

    @GET("/")
    fun getMission()


}
