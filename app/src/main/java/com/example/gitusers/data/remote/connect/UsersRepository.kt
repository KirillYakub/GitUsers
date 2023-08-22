package com.example.gitusers.data.remote.connect

import androidx.paging.PagingSource
import com.example.gitusers.data.remote.Users
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query

interface UsersRepository {

    @Headers("Content-Type: application/json")
    @GET("/users")
    suspend fun getUsers(
        @Query("page") page: Int,
        @Query("per_page") pageCount: Int,
        @Header("Authorization") token: String
    ): Response<List<Users>>
}