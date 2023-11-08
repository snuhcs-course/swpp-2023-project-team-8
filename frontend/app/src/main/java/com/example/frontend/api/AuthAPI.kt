package com.example.frontend.api

import com.example.frontend.model.AuthResponse
import com.example.frontend.model.EmailModel
import com.example.frontend.model.LoginModel
import com.example.frontend.model.RegisterModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthAPI {
    @POST("/auth/login")
    fun login(@Body loginModel: LoginModel?): Call<AuthResponse?>?

    @POST("/verification_mails")
    fun verifyEmail(@Body email: EmailModel?): Call<EmailModel?>?

    @POST("/users")
    fun register(@Body registerModel: RegisterModel?): Call<RegisterModel?>?
}
