package com.delaroystudios.cache.users

import android.os.Handler
import android.os.Looper
import com.delaroystudios.cache.data.CacheTestData.createUsersData
import com.delaroystudios.cache.db.DatabaseTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

internal class CachedUsersTest : DatabaseTest() {

    private lateinit var dao: UsersDao

    @Before
    fun init() {
        dao = database.usersDao
    }

    @Test
    fun `verify that users data is saved`() {
        val users = createUsersData()
        dao.insertUser(cachedUsers = users)

        val data = dao.fetchUsersList()
        // then we verify that the users is saved
        Assert.assertEquals(data.size, 1)
    }

    @Test
    fun `verify that all users is retrieved successfully `() {
        val waitTime = 2000L
        val users =
            arrayListOf(createUsersData(), createUsersData())

        // save all users
        dao.saveAllUsers(cachedUsers = users)

        Handler(Looper.getMainLooper()).postDelayed({
            val result = dao.fetchUsersList()
            Assert.assertEquals(result.size, users.size)
        }, waitTime)
    }

    @Test
    fun `verify that update image successfully `() {

        val id = 12
        val cachedUsers = CachedUsers(
            id = id,
            name = "Michael",
            username = "vpd",
            email = "vpd@vpd.com",
            phone = "081234455556",
            website = "ww.vpd.com",
            imageUri = ""
        )

        // save users
        dao.insertUser(cachedUsers = cachedUsers)

        val result = dao.updateUserImage(imageUri = "uri://path-to-image", id = id)

        // then we assert all that expected users is fetched
        Assert.assertEquals(result, 1)
    }
}
