package com.delaroystudios.domain.users

import com.delaroystudios.domain.common.Resource
import com.delaroystudios.domain.common.SuspendUseCase
import javax.inject.Inject

class FetchUsersUseCase @Inject constructor(private val usersRepository: UsersRepository) :
    SuspendUseCase<Unit, Resource<Unit>>() {

    override suspend fun invoke(param: Unit): Resource<Unit> = usersRepository.fetchUsers()
}