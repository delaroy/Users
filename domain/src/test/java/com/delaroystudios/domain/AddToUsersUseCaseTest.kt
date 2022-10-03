package com.delaroystudios.domain

import com.delaroystudios.domain.model.Users
import com.delaroystudios.domain.users.AddToUsersUseCase
import com.delaroystudios.domain.users.UsersP
import com.delaroystudios.domain.users.UsersRepository
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class AddToUsersUseCaseTest {

    private lateinit var addToUsersUseCase: AddToUsersUseCase
    private val usersRepository: UsersRepository = mock()

    @Before
    fun init() {
        addToUsersUseCase = AddToUsersUseCase(usersRepository)
    }

    @Test
    fun `verify that the add method in the users repository was called`() {
        runTest {
            // when we invoke the use case
            val usersParam = UsersP(
                name = "Bamidele",
                id = 8,
                username = "del",
                email = "del@del.com",
                phone = "08123344555",
                website = "www.del.com",
                imageUri = ""
            )
            addToUsersUseCase(usersParam)

            // then we verify that the repository is called
            verify(usersRepository).saveUsers(
                Users(
                    name = usersParam.name,
                    id = usersParam.id,
                    username = usersParam.username,
                    email = usersParam.email,
                    phone = usersParam.phone,
                    website = usersParam.website,
                    imageUri = usersParam.imageUri
                )
            )
        }
    }
}