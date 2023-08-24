package com.example.gitusers.data.remote

import com.example.gitusers.gitdata.local.ClientData
import com.example.gitusers.model.Users
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface UsersApi {
    @Headers("Authorization: Bearer ${ClientData.apiKey}")
    @GET("/users?")
    suspend fun getUsers(
        @Query("since") since: Int,
        @Query("per_page") perPage: Int
    ): List<Users>
}