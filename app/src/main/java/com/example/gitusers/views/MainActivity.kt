package com.example.gitusers.views

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.gitusers.common.RetrofitInstance
import com.example.gitusers.common.gitdata.ClientData
import com.example.gitusers.common.gitdata.remote.GitHubRepo
import com.example.gitusers.model.UsersListsViewModel
import com.example.gitusers.ui.theme.GitUsersTheme
import com.example.gitusers.views.DisplayText
import com.example.gitusers.views.MainPage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity()
{
    override fun onResume() {
        super.onResume()
        val myIntent = Intent(Intent.ACTION_VIEW, Uri.parse(
            "https://github.com/login/oauth/authorize?client_id=${ClientData.clientId}&scope=repo&redirect_url=${ClientData.redirectUrl}")
        )
        setContent {
            val uri: Uri? = intent.data
            if(uri == null) {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Button(
                        onClick = {
                            startActivity(myIntent)
                        }
                    ) {
                        DisplayText(text = "Connect to GitHub", 18)
                    }
                }
            }
            else {
                val code: String? = uri.getQueryParameter("code")
                var responseResult by remember { mutableStateOf(ClientData.code) }
                if(responseResult != null) {
                    val viewModel = hiltViewModel<UsersListsViewModel>()
                    val users = viewModel.usersPageFlow.collectAsLazyPagingItems()
                    MainPage(viewModel = viewModel, users = users)
                }
                LaunchedEffect(key1 = code) {
                    val response = GitHubRepo().getAuthCode(
                        clientId = ClientData.clientId,
                        clientSecret = ClientData.clientSecret,
                        code = code!!
                    )
                    if (response.isSuccessful) {
                        responseResult = response.body()!!.accessToken
                        ClientData.code = responseResult
                    }
                }
            }
        }
    }
}