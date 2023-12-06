package com.example.frontend.repository

import android.content.Context
import com.example.frontend.api.FriendService
import com.example.frontend.api.UserService
import com.example.frontend.callback.FriendLocationCallback
import com.example.frontend.interceptor.AuthInterceptor
import com.example.frontend.model.UserWithLocationModel
import com.example.frontend.utilities.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


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
            .baseUrl(BASE_URL)
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

    @Provides
    fun provideUserService(retrofit: Retrofit): UserService {
        return retrofit.create(UserService::class.java)
    }

}
