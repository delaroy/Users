package com.delaroystudios.data.common

import com.delaroystudios.data.model.UsersResponse
import com.delaroystudios.domain.model.Users
import java.util.*

internal object InventoryData {

    fun createUsersData(id: Int = generateId()): UsersResponse =
        UsersResponse(
            id = id,
            name = "Michael",
            username = "vpd",
            email = "vpd@vpd.com",
            phone = "081234455556",
            website = "ww.vpd.com",
            imageUri = ""
        )

    fun generateId(): Int {
        val rnd = Random()
        val number = rnd.nextInt(999999)

        // this will convert any number sequence into 6 character.
        return String.format(Locale.US,"%06d", number).toInt()
    }

    fun createUsers(): Users =
        Users(
            id = 5,
            name = "Michael",
            username = "vpd",
            email = "vpd@vpd.com",
            phone = "081234455556",
            website = "ww.vpd.com",
            imageUri = ""
        )
}