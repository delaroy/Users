package com.delaroystudios.data.users

import com.delaroystudios.data.api.ApiService
import com.delaroystudios.data.common.sendRequest
import com.delaroystudios.data.di.IoDispatcher
import com.delaroystudios.domain.common.Resource
import com.delaroystudios.domain.model.Users
import com.delaroystudios.domain.users.UsersRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

class UsersRepositoryImpl @Inject constructor(
    private val api: ApiService,
    private val usersCache: UsersCache,
    private val mapper: UsersEntityMapper,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : UsersRepository {

    override val users: Flow<List<Users>>
        get() = usersCache.getUsers().map { mapper.mapModelList(it) }

    override suspend fun fetchUsers(): Resource<Unit> =
        withContext(dispatcher) {
            try {
                val result = sendRequest {
                    api.fetchUsers()
                }
                val content =
                    result.data ?: return@withContext Resource.error(
                        message = "unable to fetch data"
                    )
                usersCache.saveAllUsers(content)
                return@withContext Resource.success(Unit)
            } catch (ex: Exception) {
                when (ex) {
                    is IOException -> return@withContext Resource.error("Network Failure")
                    else -> {
                        return@withContext Resource.error("unable to fetch data")
                    }
                }
            }
        }


    override suspend fun saveUsers(users: Users): Long =
        withContext(dispatcher) {
            return@withContext usersCache.saveUsers(mapper.to(users))
        }

    override suspend fun updateUserImage(imageUri: String, id: Int): Int =
        withContext(dispatcher) {
            return@withContext usersCache.updateUserImage(imageUri = imageUri, id = id)
        }

}