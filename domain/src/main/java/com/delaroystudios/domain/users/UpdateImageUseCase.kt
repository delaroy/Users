package com.delaroystudios.domain.users

import com.delaroystudios.domain.common.SuspendUseCase
import javax.inject.Inject

class UpdateImageUseCase @Inject constructor(
    private val usersRepository: UsersRepository
) : SuspendUseCase<UpdateAddParam, Unit>() {
    override suspend fun invoke(param: UpdateAddParam) {
        usersRepository.updateUserImage(param.imageUri, param.id)
    }
}

data class UpdateAddParam(val imageUri: String, val id: Int)