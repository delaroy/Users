package com.delaroystudios.cache.users

import com.delaroystudios.common.Mapper
import com.delaroystudios.data.model.UsersResponse
import javax.inject.Inject

class UsersEntityMapper @Inject constructor(
) : Mapper<CachedUsers, UsersResponse> {
    override fun from(cache: CachedUsers): UsersResponse =
        cache.run {
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

    override fun to(data: UsersResponse): CachedUsers =
        data.run {
            CachedUsers(
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