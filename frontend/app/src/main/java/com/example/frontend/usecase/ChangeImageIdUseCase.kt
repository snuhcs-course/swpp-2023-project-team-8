package com.example.frontend.usecase

import com.example.frontend.repository.UsersRepository
import javax.inject.Inject

class ChangeImageIdUseCase @Inject constructor(
    private val usersRepository: UsersRepository
) {
    suspend fun execute(imageId: Int) {
        usersRepository.changeImageId(imageId)
    }
}
