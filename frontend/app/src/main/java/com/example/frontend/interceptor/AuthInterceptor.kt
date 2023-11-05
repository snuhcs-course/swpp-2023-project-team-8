package com.example.frontend.interceptor

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AuthInterceptor(context: Context) : Interceptor {

    private val authTokenProvider = TokenProvider(context)

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        // Get the auth token from SharedPreferences or wherever it's stored
        val token = authTokenProvider.getAuthToken()

        // Add the Authorization header to the original request
        val newRequest = originalRequest.newBuilder()
            .addHeader("Authorization", "Bearer $token")
            .build()

        return chain.proceed(newRequest)
    }
}

class TokenProvider(val context: Context) {
    fun getAuthToken(): String? {
        val masterKey = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        val sharedPreferences = EncryptedSharedPreferences.create(
            context,
            "secure_app_prefs",
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )

        // Retrieve the auth token
        return sharedPreferences.getString("AUTH_TOKEN", null)
    }
}

fun createRetrofit(context: Context): Retrofit {
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