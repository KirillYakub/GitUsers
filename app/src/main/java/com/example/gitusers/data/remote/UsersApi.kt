package com.example.gitusers.data.remote

import com.example.gitusers.model.Users
import com.example.gitusers.model.UsersRepo
import com.example.gitusers.util.ApiName
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface UsersApi {
    @Headers("Authorization: Bearer ${ApiName.gitHubApiKey}")
    @GET("/users?")
    suspend fun getUsers(
        @Query("since") since: Int,
        @Query("per_page") perPage: Int
    ): List<Users>

    @Headers("Authorization: Bearer ${ApiName.gitHubApiKey}")
    @GET("/users/{username}/repos?")
    suspend fun getUsersRepos(
        @Path("username") login: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
    ): List<UsersRepo>
}