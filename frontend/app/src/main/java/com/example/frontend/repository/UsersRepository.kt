package com.example.frontend.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.frontend.api.UserService
import com.example.frontend.model.UserModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

class UsersRepository @Inject constructor(private val userService: UserService) {
    suspend fun getUsers(): List<UserModel> {
        return userService.getAllUsers()
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
}

@HiltViewModel
class UsersViewModel @Inject constructor(private val usersRepository: UsersRepository) :
    ViewModel() {

    private val _users = MutableLiveData<List<UserModel>>()
    val users: LiveData<List<UserModel>> = _users

    private val _friends = MutableLiveData<List<UserModel>>()
    val friends: LiveData<List<UserModel>> = _friends

    private val _requestStatus = MutableLiveData<String>()
    val requestStatus: LiveData<String> = _requestStatus

    private val _pendingRequests = MutableLiveData<List<UserModel>>()
    val pendingRequests: LiveData<List<UserModel>> = _pendingRequests

    fun getUsers() {
        viewModelScope.launch {
            try {
                val userList = usersRepository.getUsers()
                _users.postValue(userList)
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    fun getFriends() {
        viewModelScope.launch {
            try {
                val friendList = usersRepository.getFriends()
                _friends.postValue(friendList)
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    fun sendFriendRequest(userId: Long) {
        viewModelScope.launch {
            try {
                val response = usersRepository.sendFriendRequest(userId)
                if (response.isSuccessful) {
                    _requestStatus.postValue("Friend request has been sent")
                } else {
                    _requestStatus.postValue("이미 친구이거나 친구 요청이 존재하는 유저입니다.%${Math.random()}")
                }
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    fun getPendingRequests() {
        viewModelScope.launch {
            try {
                val pendingRequests = usersRepository.getPendingRequests()
                _pendingRequests.postValue(pendingRequests)
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    fun confirmRequest(friendId: Long) {
        viewModelScope.launch {
            try {
                usersRepository.confirmRequest(friendId)
                val updatedList = _pendingRequests.value?.filterNot { it.id == friendId }
                _pendingRequests.postValue(updatedList!!)
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    fun clearRequestStatus() {
        _requestStatus.value = null
    }


}