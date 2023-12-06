package com.example.frontend.usecase

import android.content.Context
import com.example.frontend.repository.UserContextRepository

/*
 * 로그아웃을 담당하는 유즈케이스로
 * Persistent Storage에 저장된 토큰과 데이터를 모두 삭제합니다
 */
class LogOutUseCase(
    context: Context,
) {
    private val userContextRepository = UserContextRepository.ofContext(context)
    private val secureUserContextRepository = UserContextRepository.ofContext(context, secure = true)

    fun execute() {
        userContextRepository.clear()
        secureUserContextRepository.clear()
    }
}
