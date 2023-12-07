package com.example.frontend.usecase.login

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.runtime.MutableState
import com.example.frontend.MainActivity
import com.example.frontend.api.AuthService
import com.example.frontend.model.AuthResponse
import com.example.frontend.model.LoginModel
import com.example.frontend.repository.UserContextRepository
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

        if (BYPASS_LOGIN) { // TODO(heka1024): Remove this flag
            val nextIntent = Intent(context, MainActivity::class.java)
            context.startActivity(nextIntent)
            if (context is Activity) {
                context.finish()
            }
        }

        authService.login(loginModel)!!.enqueue(object : Callback<AuthResponse?> {
            override fun onResponse(call: Call<AuthResponse?>, response: Response<AuthResponse?>) {
                if (response.isSuccessful && response.body() != null) {
                    val authToken = response.body()?.token ?: throw Exception("authToken is null")

                    saveAuthToken(authToken)
                    result.value = "Logged in successfully"

                    val nextIntent = Intent(context, MainActivity::class.java)
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
    private fun saveAuthToken(authToken: String) {
        UserContextRepository
            .ofContext(context, secure = true)
            .saveAuthToken(authToken)
        UserContextRepository
            .ofContext(context, secure = false)
            .saveIsLoggedIn(true)
    }
}
