package com.delaroystudios.domain.users

import com.delaroystudios.domain.common.Resource
import com.delaroystudios.domain.model.Users
import kotlinx.coroutines.flow.Flow

interface UsersRepository {

    val users: Flow<List<Users>>

    suspend fun fetchUsers(): Resource<Unit>

    suspend fun saveUsers(users: Users): Long

    suspend fun updateUserImage(imageUri: String, id: Int): Int
}