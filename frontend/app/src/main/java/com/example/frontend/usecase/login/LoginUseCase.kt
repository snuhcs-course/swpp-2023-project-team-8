package com.example.frontend.usecase.login

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.runtime.MutableState
import com.example.frontend.MapActivity
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
                    val authToken = response.body()?.token ?: throw Exception("authToken is null")
                    val userName = response.body()?.userName ?: throw Exception("userName is null")
                    val userMail = response.body()?.userMail ?: throw Exception("userMail is null")
                    val userProfile = response.body()?.userProfile

                    saveAuthToken(authToken, userName, userMail, userProfile ?: -1)
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
    private fun saveAuthToken(authToken: String, userName: String, userMail: String, userProfile: Int) {
        UserContextRepository
            .ofContext(context, secure = true)
            .saveAuthToken(authToken)

        UserContextRepository.ofContext(context, secure = false).apply {
            saveUserName(userName)
            saveUserMail(userMail)
            saveSelectedPredefinedImage(userProfile)
            saveIsLoggedIn(true)
        }
    }
}
