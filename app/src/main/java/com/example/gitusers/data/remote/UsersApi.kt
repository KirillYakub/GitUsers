package com.example.gitusers.data.remote

import com.example.gitusers.model.Users
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query

interface UsersApi {
    @Headers("Authorization: Bearer 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")
    @GET("/users")
    suspend fun getUsers(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
    ): List<Users>

    @GET("/search/users")
    suspend fun searchUsers(
        @Query("page") page: Int,
        @Query("per_page") pageCount: Int,
        @Header("Authorization") token: String
    ): List<Users>
}