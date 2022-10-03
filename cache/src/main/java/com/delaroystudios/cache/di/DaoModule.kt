package com.delaroystudios.cache.di

import com.delaroystudios.cache.db.UsersDatabase
import com.delaroystudios.cache.users.UsersDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DaoModule {

    @[Provides Singleton]
    fun providesUsersDao(
        database: UsersDatabase,
    ): UsersDao = database.usersDao
}
