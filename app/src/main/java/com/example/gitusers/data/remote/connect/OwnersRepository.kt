package com.example.gitusers.data.remote.connect

import com.example.gitusers.data.local.LocalOwner
import com.example.gitusers.data.remote.Owner
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query

interface OwnersRepository {
    @Headers("Content-Type: application/json")
    @GET("/users")
    suspend fun getUsers(
        @Query("page") page: Int,
        @Query("per_page") pageCount: Int,
        @Header("Authorization") token: String
    ): Response<List<Owner>>
}