package com.example.frontend.api

import com.example.frontend.model.AuthResponse
import com.example.frontend.model.EmailModel
import com.example.frontend.model.LoginModel
import com.example.frontend.model.RegisterModel
import com.example.frontend.utilities.BASE_URL
import com.example.frontend.utilities.GsonProvider
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Used to connect to the server
 * About authentication domain
 */
interface AuthService {
    @POST("/auth/login")
    fun login(@Body loginModel: LoginModel?): Call<AuthResponse?>?

    // TODO: rename 추가
    @POST("/rename")
    fun rename(@Body newName: String?)

    @POST("/verification_mails")
    fun verifyEmail(@Body email: String): Call<EmailModel?>?

    @POST("/users")
    fun register(@Body registerModel: RegisterModel?): Call<RegisterModel>

    companion object {
        fun create(): AuthService {
            val logger = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(GsonProvider.gson))
                .build()
                .create(AuthService::class.java)
        }
    }
}
