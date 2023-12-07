package com.example.frontend.usecase

import android.content.Context
import android.util.Log
import com.example.frontend.repository.UserContextRepository
import com.example.frontend.repository.UsersRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

/*
 * users/me API를 바탕으로 사용자 정보를 저장하는 UseCase
 */
class SaveMyInfoUseCase @Inject constructor(
    private val repository: UsersRepository,
    @ApplicationContext context: Context
) {
    private val userContextRepository = UserContextRepository.ofContext(context)

    suspend fun execute() {
        Log.i("SaveMyInfoUseCase", "execute")

        repository.getMyInfo().apply {
            userContextRepository.saveUserName(name)
            userContextRepository.saveUserMail(email)
            userContextRepository.saveUserId(id.toInt())
        }
    }
}
