package com.example.frontend.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.frontend.callback.FriendLocationCallback
import com.example.frontend.model.UserWithLocationModel
import com.example.frontend.repository.FriendsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FriendsViewModel @Inject constructor(private val repository: FriendsRepository) :
    ViewModel() {
    private val _friendsList = MutableLiveData<List<UserWithLocationModel>>()
    val friendsList: LiveData<List<UserWithLocationModel>> = _friendsList

    fun fetchFriends() {
        repository.getNearbyFriends(object : FriendLocationCallback {
            override fun onResult(result: List<UserWithLocationModel>) {
                _friendsList.postValue(result)
            }

            override fun onError(error: Throwable) {
                // Handle error
            }
        })
    }
}
