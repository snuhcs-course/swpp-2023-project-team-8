package com.example.frontend.usecase

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.example.frontend.MapActivity
import com.example.frontend.api.AuthService
import com.example.frontend.model.AuthResponse
import com.example.frontend.model.LoginModel
import com.example.frontend.utilities.BYPASS_LOGIN
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/*
 * 로그인을 담당하는 유즈케이스
 */
class LoginUseCase(
    private val context: Context,
    private val email: String,
    private val password: String,
    private val result: MutableState<String>,
    private val authService: AuthService = AuthService.create()
) {
    fun execute() {
        val loginModel = LoginModel(email, password)
        val call = authService.login(loginModel)

        if (BYPASS_LOGIN) { // TODO(heka1024): Remove this flag
            val nextIntent = Intent(context, MapActivity::class.java)
            context.startActivity(nextIntent)
            if (context is Activity) {
                context.finish()
            }
        }
        call!!.enqueue(object : Callback<AuthResponse?> {
            override fun onResponse(call: Call<AuthResponse?>, response: Response<AuthResponse?>) {
                if (response.isSuccessful && response.body() != null) {
                    val authToken = response.body()?.token
                    val userName = response.body()?.userName
                    val userMail = response.body()?.userMail
                    val userProfile = response.body()?.userProfile

                    if (authToken != null) {
                        saveAuthToken(authToken, userName, userMail, userProfile?:-1)
                    }
                    result.value = "Logged in successfully"

                    val nextIntent = Intent(context, MapActivity::class.java)
                    context.startActivity(nextIntent)

                    if (context is Activity) {
                        context.finish()
                    }
                } else {
                    // Handle login failure
                    result.value = "Login failed: " + response.errorBody()?.string()
                    Toast.makeText(context, result.value, Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<AuthResponse?>, t: Throwable) {
                result.value = "Login error: " + t.message
                Toast.makeText(context, result.value, Toast.LENGTH_LONG).show()
            }
        })
    }


    // To save the auth token securely when logging in
    private fun saveAuthToken(authToken: String, userName: String?, userMail: String?, userProfile :Int) {
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

        // Save auth token securely
        with(sharedPreferences.edit()) {
            putString("AUTH_TOKEN", authToken)
            putString("USERNAME", userName)
            putString("USER_MAIL", userMail)
            putInt("SELECTED_PREDEFINED_IMAGE", userProfile)
            apply()
        }

        // Update login state
        val appPrefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        with(appPrefs.edit()) {
            putBoolean("IS_LOGGED_IN", true)
            putString("USERNAME", userName)
            apply()
        }
    }
}