package com.delaroystudios.domain.users

import com.delaroystudios.domain.common.UseCase
import com.delaroystudios.domain.model.Users
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUsersUseCase @Inject constructor(
    private val usersRepository: UsersRepository
) : UseCase<Unit, Flow<List<Users>>>() {
    override fun invoke(param: Unit): Flow<List<Users>> =
        usersRepository.users
}