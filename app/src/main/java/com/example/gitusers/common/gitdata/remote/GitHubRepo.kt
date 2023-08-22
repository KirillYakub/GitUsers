package com.example.gitusers.common.gitdata.remote

import com.example.gitusers.common.RetrofitInstance
import com.example.gitusers.common.gitdata.AccessToken
import retrofit2.Response

class GitHubRepo {
    suspend fun getAuthCode(
        clientId:String,
        clientSecret:String,
        code:String
    ): Response<AccessToken> {
        return RetrofitInstance.gitInstance.getAccessToken(clientId, clientSecret, code)
    }
}