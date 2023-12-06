package com.example.frontend.usecase.login

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.MutableState
import com.example.frontend.api.AuthService
import com.example.frontend.model.RegisterModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/*
 * 회원 가입을 담당한다
 */
class RegisterUseCase(
    private val context: Context,
    private val email: String,
    private val code: String,
    private val name: String,
    private val password: String,
    private val result: MutableState<String>,
    private val onSwitchToLogin: () -> Unit,
    private val authService: AuthService = AuthService.create()
) {
    fun execute() {
        val registerModel = RegisterModel(email, code, name, password)
        authService.register(registerModel).enqueue(object : Callback<RegisterModel?> {
            override fun onResponse(call: Call<RegisterModel?>, response: Response<RegisterModel?>) {
                result.value = "Response Code: " + response.code()
                Toast.makeText(context, "회원가입 성공!", Toast.LENGTH_LONG).show()
                onSwitchToLogin()
            }

            override fun onFailure(call: Call<RegisterModel?>, t: Throwable) {
                result.value = "Error: " + t.message
                Toast.makeText(context, result.value, Toast.LENGTH_LONG).show()
            }
        })
    }
}
