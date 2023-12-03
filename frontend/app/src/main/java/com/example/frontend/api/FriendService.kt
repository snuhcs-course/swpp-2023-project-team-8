package com.example.frontend.api

import com.example.frontend.model.UserWithLocationModel
import retrofit2.Call
import retrofit2.http.GET

interface FriendAPI {

    @GET("/friends/nearby")
    fun getNearbyFriends(): Call<List<UserWithLocationModel>>

}