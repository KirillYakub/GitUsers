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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.ExperimentalPagingApi
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.gitusers.gitdata.local.ClientData
import com.example.gitusers.gitdata.local.GitHubRepo
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity: ComponentActivity() {

    @OptIn(ExperimentalPagingApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            //StartIntent()
            val viewModel: UsersViewModel = hiltViewModel()
            val users = viewModel.getAllUsers().collectAsLazyPagingItems()
            DisplayUsers(items = users)
        }
    }

    /*
    override fun onResume() {
        super.onResume()
        setContent {
            if (intent?.data != null) MainScreen(intent.data) else StartIntent()
        }
    }

    @Composable
    private fun StartIntent() {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(
            "https://github.com/login/oauth/authorize?client_id=${ClientData.clientId}&scope=repo&redirect_url=${ClientData.redirectUrl}")
        )
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Button(
                onClick = {
                    startActivity(intent)
                }
            ) {
                DisplayText(text = "Connect to GitHub", 18)
            }
        }
    }

    @OptIn(ExperimentalPagingApi::class)
    @Composable
    fun MainScreen(uri: Uri?) {
        val code: String? = uri?.getQueryParameter("code")
        val viewModel: UsersViewModel = hiltViewModel()

        if(code != null) {
            viewModel.exchangeAuthCode(code)

            val accessTokenState = viewModel.accessToken.observeAsState()
            if (accessTokenState.value != null) {
                val users = viewModel.getAllUsers().collectAsLazyPagingItems()
                DisplayUsers(items = users)
            } else {
                DisplayText(
                    text = "Wait a few seconds. If nothing won't happen reopen the app",
                    size = 18
                )
            }

        }
        else
            StartIntent()
    }
    */
}