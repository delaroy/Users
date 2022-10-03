package com.delaroystudios.data.api

import com.delaroystudios.data.api.EndPoint.USERS
import com.delaroystudios.data.model.UsersResponse
import retrofit2.http.GET

interface ApiService {
    @GET(USERS)
    suspend fun fetchUsers(): List<UsersResponse>
}
