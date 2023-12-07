package com.example.frontend.usecase

import android.content.Context
import com.example.frontend.repository.UsersRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ChangeImageIdUseCase @Inject constructor(
    @ApplicationContext context: Context,
    private val usersRepository: UsersRepository
) {
    suspend fun execute(imageId: Int) {
        usersRepository.changeImageId(imageId)
    }
}
