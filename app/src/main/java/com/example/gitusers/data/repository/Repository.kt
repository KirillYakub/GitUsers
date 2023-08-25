package com.example.gitusers.data.repository

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.example.gitusers.data.UsersDatabase
import com.example.gitusers.data.paging.UsersMediator
import com.example.gitusers.data.paging.UsersReposMediator
import com.example.gitusers.data.remote.UsersApi
import com.example.gitusers.model.LocalUsers
import com.example.gitusers.model.LocalUsersRepo
import com.example.gitusers.util.User
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ExperimentalPagingApi
class Repository @Inject constructor(
    private val usersApi: UsersApi,
    private val usersDatabase: UsersDatabase
) {
    fun getAllUsers(): Flow<PagingData<LocalUsers>> {
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

    fun getAllUsersRepos(): Flow<PagingData<LocalUsersRepo>> {
        val pagingSourceFactory = {
            usersDatabase.usersReposDao().getAllUsersRepos(User.activeUser!!.id)
        }
        return Pager(
            config = PagingConfig(pageSize = 5),
            remoteMediator = UsersReposMediator(
                usersApi = usersApi,
                usersDatabase = usersDatabase
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }
}