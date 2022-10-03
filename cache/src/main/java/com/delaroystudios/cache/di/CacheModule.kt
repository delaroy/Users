package com.delaroystudios.cache.di

import com.delaroystudios.cache.users.UsersCacheImpl
import com.delaroystudios.data.users.UsersCache
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent



@InstallIn(SingletonComponent::class)
@Module
interface CacheModule {

    @get:[Binds]
    val UsersCacheImpl.bindUsersCache: UsersCache

}
