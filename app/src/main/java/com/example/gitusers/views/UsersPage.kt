package com.example.gitusers.views

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemKey
import coil.compose.AsyncImage
import com.example.gitusers.model.Users

@Composable
fun DisplayText(text: String, size: Int) {
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

@Composable
fun DisplayUsers(items: LazyPagingItems<Users>) {
    Log.d("COUNT", items.itemCount.toString())
    LaunchedEffect(key1 = items.itemCount)
    {
        Log.d("COUNT", items.itemCount.toString())
    }
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(
            count = items.itemCount,
            key = items.itemKey { it.id }
        ) {id ->
            items[id]?.let { UsersItem(user = it) }
        }
    }
}

@Composable
fun UsersItem(user: Users) {
    Card {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            AsyncImage(
                model = user.avatarUrl,
                contentDescription = null,
                modifier = Modifier
                    .size(72.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                DisplayText(text = user.login, size = 24)
                DisplayText(text = user.reposUrl, size = 18)
            }
        }
    }
}