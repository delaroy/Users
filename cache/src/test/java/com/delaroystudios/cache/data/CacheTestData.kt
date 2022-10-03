package com.delaroystudios.cache.data

import com.delaroystudios.cache.users.CachedUsers
import java.util.*

internal object CacheTestData {

    fun generateCacheId(): Int {
        val rnd = Random()
        val number = rnd.nextInt(999999)

        // this will convert any number sequence into 6 character.
        return String.format(Locale.US,"%06d", number).toInt()
    }

    fun createUsersData(id: Int = generateCacheId()): CachedUsers =
        CachedUsers(
            id = id,
            name = "Michael",
            username = "vpd",
            email = "vpd@vpd.com",
            phone = "081234455556",
            website = "ww.vpd.com",
            imageUri = ""
        )
}
