package com.delaroystudios.domain.users

import com.delaroystudios.domain.common.SuspendUseCase
import com.delaroystudios.domain.model.Users
import javax.inject.Inject

class AddToUsersUseCase @Inject constructor(
    private val usersRepository: UsersRepository
) : SuspendUseCase<UsersP, Unit>() {
    override suspend fun invoke(param: UsersP) {
        usersRepository.saveUsers(
            Users(
                name = param.name,
                id = param.id,
                username = param.username,
                email = param.email,
                phone = param.phone,
                website = param.website,
                imageUri = param.imageUri
            )
        )
    }
}

data class UsersP( val id: Int,
                   val name: String,
                   val username: String,
                   val email: String,
                   val phone: String,
                   val website: String,
                   val imageUri: String = "")