package com.example.frontend.api

import com.example.frontend.model.AuthResponse
import com.example.frontend.model.EmailModel
import com.example.frontend.model.LoginModel
import com.example.frontend.model.RegisterModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface FriendAPI {
    @POST("/confirm")
    fun confirm(@Body confirmed: Boolean): Boolean

//    @GET("/friends/places")
//    fun getFriends(@Body loginModel: LoginModel?): Call<AuthResponse?>?

//    @GET("/friends/nearby.json")
//    fun listNearbyFriends
}
