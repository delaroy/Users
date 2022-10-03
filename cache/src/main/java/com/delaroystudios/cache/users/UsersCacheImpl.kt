package com.delaroystudios.cache.users

import com.delaroystudios.data.model.UsersResponse
import com.delaroystudios.data.users.UsersCache
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UsersCacheImpl @Inject constructor(
    private val dao: UsersDao,
    private val mapper: UsersEntityMapper
) : UsersCache {

    override fun getUsers(): Flow<List<UsersResponse>> =
        dao.fetchUsers().map {
            mapper.mapModelList(it)
        }

    override suspend fun saveUsers(users: UsersResponse): Long =
        dao.insertUser(mapper.to(users))

    override suspend fun saveAllUsers(users: List<UsersResponse>): List<Long> =
        dao.saveAllUsers(mapper.mapEntityList(users))

    override suspend fun updateUserImage(imageUri: String, id: Int): Int =
        dao.updateUserImage(imageUri = imageUri, id = id)
}
