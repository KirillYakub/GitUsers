package com.example.gitusers.data.repository

import androidx.lifecycle.ViewModel
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.gitusers.data.UsersDatabase
import com.example.gitusers.data.paging.UsersMediator
import com.example.gitusers.data.remote.UsersApi
import com.example.gitusers.model.Users
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ExperimentalPagingApi
class Repository @Inject constructor(
    private val usersApi: UsersApi,
    private val usersDatabase: UsersDatabase
) {
    fun getAllUsers(): Flow<PagingData<Users>> {
        val pagingSourceFactory = { usersDatabase.usersDao().getAllUsers() }
        return Pager(
            config = PagingConfig(pageSize = 10),
            remoteMediator = UsersMediator(
                usersApi = usersApi,
                usersDatabase = usersDatabase
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }
}