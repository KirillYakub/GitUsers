package com.example.gitusers.gitdata.local

import com.example.gitusers.gitdata.remote.AccessToken
import com.example.gitusers.gitdata.remote.GitHubClient
import retrofit2.Response
import javax.inject.Inject

class GitHubRepo @Inject constructor(
    private val git: GitHubClient
) {
    suspend fun getAuthCode(
        clientId:String,
        clientSecret:String,
        code:String
    ): Response<AccessToken> {
        return git.getAccessToken(clientId, clientSecret, code)
    }
}