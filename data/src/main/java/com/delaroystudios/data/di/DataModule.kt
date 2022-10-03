package com.delaroystudios.data.di

import com.delaroystudios.data.users.UsersRepositoryImpl
import com.delaroystudios.domain.users.UsersRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@InstallIn(SingletonComponent::class)
@Module
interface DataModule {

    @get:[Binds]
    val UsersRepositoryImpl.bindUsersRepository: UsersRepository

}
