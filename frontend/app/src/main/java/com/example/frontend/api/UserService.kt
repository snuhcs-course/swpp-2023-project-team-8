package com.example.frontend.api

import com.example.frontend.model.ImageChangeRequest
import com.example.frontend.model.UserModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface UserService {
    @GET("/users")
    suspend fun getAllUsers(): List<UserModel>

    @GET("/friends")
    suspend fun getAllFriends(): List<UserModel>

    @POST("/friends")
    suspend fun sendFriendRequest(@Body userId: UserModel): Response<Unit>

    @GET("/users/me/pending_requests")
    suspend fun getPendingRequests(): List<UserModel>

    @POST("/friends/{id}/confirm")
    suspend fun confirmRequest(@Path("id") friendId: Long)

    @PUT("/users/me/change_image_id")
    suspend fun changeImageId(@Body imageId: ImageChangeRequest): Response<Unit>
}
