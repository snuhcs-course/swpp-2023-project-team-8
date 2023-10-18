package com.example.frontend.api

import com.example.frontend.model.LoginModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthAPI {
    @POST("/auth/login")
    fun login(@Body loginModel: LoginModel?): Call<LoginModel?>?
}