package com.example.frontend.repository

import android.content.Context
import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.frontend.api.FriendService
import com.example.frontend.callback.FriendLocationCallback
import com.example.frontend.data.data
import com.example.frontend.interceptor.AuthInterceptor
import com.example.frontend.model.UserWithLocationModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject


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

class InviteFriendViewModel : ViewModel() {

    private val _checkedStates = mutableStateMapOf<Long, Boolean>()
    private val _checkedStatesFlow = MutableStateFlow<Map<Long, Boolean>>(_checkedStates)

    val checkedStatesFlow = _checkedStatesFlow.asStateFlow()
    fun updateCheckedState(friendId: Long, isChecked: Boolean) {
        _checkedStates[friendId] = isChecked
        _checkedStatesFlow.value = _checkedStates.toMap()
    }
}
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideRetrofit(@ApplicationContext context: Context): Retrofit {
        val authInterceptor = AuthInterceptor(context)

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl("http://10.0.2.2:3000")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun provideFriendsRepository(api: FriendService): FriendsRepository {
        return FriendsRepository(api)
    }

    @Provides
    fun provideFriendAPI(retrofit: Retrofit): FriendService {
        return retrofit.create(FriendService::class.java)
    }
}
