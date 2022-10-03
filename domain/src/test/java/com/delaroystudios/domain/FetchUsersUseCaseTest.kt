package com.delaroystudios.domain

import com.delaroystudios.domain.common.Resource
import com.delaroystudios.domain.users.FetchUsersUseCase
import com.delaroystudios.domain.users.UsersRepository
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class FetchUsersUseCaseTest {

    private lateinit var fetchUsersUseCase: FetchUsersUseCase
    private val usersRepository: UsersRepository = mock()

    @Before
    fun init() {
        fetchUsersUseCase = FetchUsersUseCase(usersRepository)
    }

    @Test
    fun `verify that users returns value on success`() {
        runTest {
            whenever(
                usersRepository.fetchUsers()
            )
                .thenReturn(Resource.success(Unit))

            // when we invoke the use case
            val result = fetchUsersUseCase(Unit)

            // then assert that the fetch is successful
            Assert.assertTrue(result.isSuccess())
        }
    }
}
