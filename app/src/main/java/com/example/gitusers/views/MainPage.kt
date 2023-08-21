package com.example.gitusers.views

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import coil.compose.AsyncImage
import com.example.gitusers.common.Resource
import com.example.gitusers.data.local.LocalOwner
import com.example.gitusers.model.UsersListsViewModel

@Composable
fun MainPage(
    viewModel: UsersListsViewModel,
    users: LazyPagingItems<LocalOwner>
) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        val context = LocalContext.current
        LaunchedEffect(key1 = users.loadState) {
            if(users.loadState.refresh is LoadState.Error) {
                Toast.makeText(
                    context,
                    "Error: " + (users.loadState.refresh as LoadState.Error).error.message,
                    Toast.LENGTH_LONG
                ).show()
                Log.d("SAD", "Error: " + (users.loadState.refresh as LoadState.Error).error.message)
            }
        }
        if(users.loadState.refresh is LoadState.Loading)
            CircularProgressIndicator(strokeWidth = 2.dp)
        else {
            val usersState by viewModel.state.collectAsState()
            when (usersState) {
                is Resource.Loading -> CircularProgressIndicator(strokeWidth = 2.dp)
                is Resource.Success -> ShowList(usersState.data!!)
                else -> DefaultText(text = usersState.message!!)
            }
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp),
        contentAlignment = Alignment.BottomEnd
    ) {
        FilledIconButton(
            onClick = {

            },
            shape = CircleShape,
            modifier = Modifier
                .size(48.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Refresh,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(5.dp)
            )
        }
    }
}

@Composable
fun ShowList(usersList: List<LocalOwner>)
{
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(usersList.size) {position ->
            Card {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(15.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AsyncImage(
                        model = usersList[position].avatarUrl,
                        contentDescription = null,
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                    DefaultText(text = usersList[position].login)
                }
            }
        }
    }
}

@Composable
fun DefaultText(text: String)
{
    Text(
        text = text,
        style = TextStyle(
            fontWeight = FontWeight.Medium,
            fontSize = 24.sp
        )
    )
}