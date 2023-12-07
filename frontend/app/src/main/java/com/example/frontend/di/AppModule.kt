package com.example.frontend.di

import android.content.Context
import com.example.frontend.api.FriendService
import com.example.frontend.api.UserService
import com.example.frontend.interceptor.AuthInterceptor
import com.example.frontend.repository.FriendsRepository
import com.example.frontend.utilities.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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
