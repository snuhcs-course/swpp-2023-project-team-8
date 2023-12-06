package com.example.frontend.usecase

import android.content.Context
import com.example.frontend.repository.UserContextRepository
import com.example.frontend.repository.UsersRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SaveMyInfoUseCase @Inject constructor(
    private val repository: UsersRepository,
    @ApplicationContext context: Context
) {
    private val userContextRepository = UserContextRepository.ofContext(context)

    suspend fun execute() {
        repository.getMyInfo().apply {
            userContextRepository.saveUserName(name)
            userContextRepository.saveUserMail(email)
            userContextRepository.saveUserId(id.toInt())
        }
    }
}
