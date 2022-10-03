package com.delaroystudios.cache.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.delaroystudios.cache.db.UsersDatabase.Companion.DATABASE_VERSION
import com.delaroystudios.cache.users.CachedUsers
import com.delaroystudios.cache.users.UsersDao

@Database(
    entities = [
        CachedUsers::class
    ],
    version = DATABASE_VERSION
)

abstract class UsersDatabase : RoomDatabase() {

    abstract val usersDao: UsersDao

    companion object {

        const val DATABASE_VERSION = 1

        private const val DATABASE_NAME = "users_database"

        @Volatile
        private var database: UsersDatabase? = null

        fun build(context: Context): UsersDatabase {
            return database ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UsersDatabase::class.java,
                    DATABASE_NAME
                ).fallbackToDestructiveMigration().build()
                database = instance
                instance
            }
        }
    }
}