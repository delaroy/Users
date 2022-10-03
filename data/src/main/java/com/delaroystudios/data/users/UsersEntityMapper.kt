package com.delaroystudios.data.users

import com.delaroystudios.common.Mapper
import com.delaroystudios.data.model.UsersResponse
import com.delaroystudios.domain.model.Users
import javax.inject.Inject

class UsersEntityMapper @Inject constructor() : Mapper<UsersResponse, Users> {
    override fun to(domain: Users): UsersResponse = domain.run {
        UsersResponse(
            id = id,
            name = name,
            username = username,
            email = email,
            phone = phone,
            website = website,
            imageUri = imageUri
        )
    }

    override fun from(entity: UsersResponse): Users = entity.run {
        Users(
            id = id,
            name = name,
            username = username,
            email = email,
            phone = phone,
            website = website,
            imageUri = imageUri
        )
    }

}