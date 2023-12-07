package com.example.frontend.repository

import com.example.frontend.api.FriendService
import com.example.frontend.callback.FriendLocationCallback
import com.example.frontend.model.UserWithLocationModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FriendsRepository(private val friendService: FriendService) {

    fun getNearbyFriends(callback: FriendLocationCallback) {
        val call = friendService.getNearbyFriends()

        call.enqueue(object : Callback<List<UserWithLocationModel>> {
            override fun onResponse(
                call: Call<List<UserWithLocationModel>>,
                response: Response<List<UserWithLocationModel>>
            ) {
                if (response.isSuccessful) {
                    callback.onResult(response.body()!!)
                } else {
                    callback.onError(RuntimeException("Response not successful"))
                }
            }

            override fun onFailure(call: Call<List<UserWithLocationModel>>, t: Throwable) {
                callback.onError(t)
            }
        })
    }
}
