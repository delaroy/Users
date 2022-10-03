package com.delaroystudios.domain

import com.delaroystudios.domain.users.GetUsersUseCase
import com.delaroystudios.domain.users.UsersRepository
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GetUsersUseCaseTest {

    private lateinit var getUsersUseCase: GetUsersUseCase
    private val usersRepository: UsersRepository = mock()

    @Before
    fun init() {
        getUsersUseCase = GetUsersUseCase(usersRepository)
    }

    @Test
    fun `verify that the get users method in the repository was called`() {
        runTest {
            // when we invoke the use case
            getUsersUseCase(Unit)

            // then we verify that the repository is called
            verify(usersRepository).users
        }
    }
}