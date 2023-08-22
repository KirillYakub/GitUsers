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
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
                    "Sorry, something went wrong",
                    Toast.LENGTH_LONG
                ).show()
                Log.d("LAUNCHED_ERROR", (users.loadState.refresh as LoadState.Error).error.message.toString())
            }
        }
        if(users.loadState.refresh is LoadState.Loading)
            CircularProgressIndicator(strokeWidth = 2.dp)
        else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(15.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(users.itemCount) {position ->
                    Card {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(15.dp),
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            AsyncImage(
                                model = users[position]!!.avatarUrl,
                                contentDescription = null,
                                modifier = Modifier
                                    .size(48.dp)
                                    .clip(CircleShape),
                                contentScale = ContentScale.Crop
                            )
                            DisplayText(text = users[position]!!.login, 18)
                        }
                    }
                }
                item {
                    if(users.loadState.append is LoadState.Loading)
                        CircularProgressIndicator(strokeWidth = 2.dp)
                }
            }
        }
    }
}

@Composable
fun DisplayText(text: String, size: Int)
{
    Text(
        text = text,
        style = TextStyle(
            fontWeight = FontWeight.Medium,
            fontSize = size.sp
        ),
        modifier = Modifier
            .padding(15.dp)
    )
}