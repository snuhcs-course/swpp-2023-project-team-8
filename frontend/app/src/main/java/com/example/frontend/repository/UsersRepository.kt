package com.example.frontend.repository

import com.example.frontend.api.UserService
import com.example.frontend.model.ImageChangeRequest
import com.example.frontend.model.UserModel
import retrofit2.Response
import javax.inject.Inject

class UsersRepository @Inject constructor(private val userService: UserService) {
    suspend fun getUsers(): List<UserModel> {
        return userService.getAllUsers()
    }

    suspend fun getMyInfo(): UserModel {
        return userService.getMyInfo()
    }

    suspend fun getFriends(): List<UserModel> {
        return userService.getAllFriends()
    }

    suspend fun sendFriendRequest(userId: Long): Response<Unit> {
        return userService.sendFriendRequest(UserModel(userId, "", ""))
    }

    suspend fun getPendingRequests(): List<UserModel> {
        return userService.getPendingRequests()
    }

    suspend fun confirmRequest(friendId: Long) {
        return userService.confirmRequest(friendId)
    }

    suspend fun changeImageId(imageId: Int): Response<Unit> {
        return userService.changeImageId(ImageChangeRequest(imageId))
    }
}

