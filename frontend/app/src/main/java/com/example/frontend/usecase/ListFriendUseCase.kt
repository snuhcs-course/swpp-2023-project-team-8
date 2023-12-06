package com.example.frontend.usecase

import android.content.Context
import com.example.frontend.api.FriendService
import com.example.frontend.data.defaultfriendList
import com.example.frontend.model.UserWithLocationModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListFriendUseCase (
    private val context: Context,
    private val friendService: FriendService = FriendService.create()
){

    fun fetch(onFriendFetched: (List<UserWithLocationModel>) -> Unit) {
        val call = friendService.getAllFriends()

        call.enqueue(object : Callback<List<UserWithLocationModel>> {
            override fun onResponse(
                call: Call<List<UserWithLocationModel>>,
                response: Response<List<UserWithLocationModel>>
            ) {
                if (response.isSuccessful) {
                    val friends = response.body() ?: emptyList()
                    onFriendFetched(friends)
                } else {
                    onFriendFetched(defaultfriendList)
                }
            }

            override fun onFailure(call: Call<List<UserWithLocationModel>>, t: Throwable) {
                onFriendFetched(defaultfriendList)
            }
        })
    }


}