package com.example.frontend.callback

import com.example.frontend.model.UserWithLocationModel

interface FriendLocationCallback {
    fun onResult(result: List<UserWithLocationModel>)
    fun onError(error: Throwable)
}