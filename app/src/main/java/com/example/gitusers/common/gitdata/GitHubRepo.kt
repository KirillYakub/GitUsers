package com.example.gitusers.common.gitdata

import com.example.gitusers.common.RetrofitInstance
import com.example.gitusers.common.gitdata.remote.AccessToken
import retrofit2.Response

class GitHubRepo {
    suspend fun getAuthCode(
        clientId:String,
        clientSecret:String,
        code:String
    ): Response<AccessToken> {
        return RetrofitInstance.gitClient.getAccessToken(clientId, clientSecret, code)
    }
}