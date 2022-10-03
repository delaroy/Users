package com.delaroystudios.data.users

import com.delaroystudios.data.model.UsersResponse
import kotlinx.coroutines.flow.Flow


interface UsersCache {

    fun getUsers(): Flow<List<UsersResponse>>

    suspend fun saveUsers(users: UsersResponse): Long

    suspend fun saveAllUsers(users: List<UsersResponse>): List<Long>

    suspend fun updateUserImage(imageUri: String, id: Int): Int
}