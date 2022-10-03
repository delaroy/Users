package com.delaroystudios.cache.users

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface UsersDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertUser(cachedUsers: CachedUsers): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun saveAllUsers(cachedUsers: List<CachedUsers>): List<Long>

    @Query("SELECT * FROM users")
    fun fetchUsers(): Flow<List<CachedUsers>>

    @Query("SELECT * FROM users")
    fun fetchUsersList(): List<CachedUsers>

    @Query("UPDATE users SET imageUri = :imageUri WHERE id = :id")
    fun updateUserImage(imageUri: String, id: Int): Int
}