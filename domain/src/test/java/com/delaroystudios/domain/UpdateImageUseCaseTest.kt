package com.delaroystudios.domain

import com.delaroystudios.domain.users.UpdateAddParam
import com.delaroystudios.domain.users.UpdateImageUseCase
import com.delaroystudios.domain.users.UsersRepository
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class UpdateImageUseCaseTest {

    private lateinit var updateImageUseCase: UpdateImageUseCase
    private val usersRepository: UsersRepository = mock()

    @Before
    fun init() {
        updateImageUseCase = UpdateImageUseCase(usersRepository)
    }

    @Test
    fun `verify that the update method in the repository was called`() {
        runTest {
            // when we invoke the use case
            val updateParam = UpdateAddParam(imageUri = "uri://path-to-image", id = 5)
            updateImageUseCase(updateParam)

            // then we verify that the repository is called
            verify(usersRepository).updateUserImage(
                updateParam.imageUri,
                updateParam.id
            )
        }
    }
}