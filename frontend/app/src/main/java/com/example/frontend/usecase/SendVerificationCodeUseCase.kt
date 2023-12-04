package com.example.frontend.usecase

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.MutableState
import com.example.frontend.api.AuthService
import com.example.frontend.model.EmailModel
import com.example.frontend.utilities.HttpStatus
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/*
 * 이메일 인증 코드를 보내는 유스케이스
 */
class SendVerificationCodeUseCase(
    private val context: Context,
    private val email: String,
    private val isWaitingForResponse: MutableState<Boolean>,
    private val authService: AuthService = AuthService.create()
) {
    fun execute() {
        authService.verifyEmail(EmailModel(email)).enqueue(object : Callback<EmailModel?> {
            override fun onResponse(call: Call<EmailModel?>, response: Response<EmailModel?>) {
                if (HttpStatus.fromCode(response.code()) != HttpStatus.CREATED) {
                    Toast.makeText(context, "에러가 발생했어요.", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(context, "인증 코드가 전송되었습니다!", Toast.LENGTH_LONG).show()
                }
                isWaitingForResponse.value = false
            }

            override fun onFailure(call: Call<EmailModel?>, t: Throwable) {
                Toast.makeText(context, "에러가 발생했어요.", Toast.LENGTH_LONG).show()
                isWaitingForResponse.value = false
            }
        })
    }
}
