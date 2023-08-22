package com.example.gitusers.data.local

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.gitusers.common.gitdata.ClientData
import com.example.gitusers.data.UsersDatabase
import com.example.gitusers.data.remote.connect.UsersRepository
import com.example.gitusers.data.remote.toLocalOwner

@OptIn(ExperimentalPagingApi::class)
class UsersMediator(
    private val usersDb: UsersDatabase,
    private val gitApi: UsersRepository
) : RemoteMediator<Int, LocalUser>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, LocalUser>
    ): MediatorResult {
        return try {
            val loadKey = when(loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(
                    endOfPaginationReached = true
                )
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    if(lastItem == null) 1 else (lastItem.id / state.config.pageSize) + 1
                }
            }
            val response = gitApi.getUsers(
                page = loadKey,
                pageCount = state.config.pageSize,
                token = ClientData.code!!
            )
            usersDb.withTransaction {
                if(loadType == LoadType.REFRESH)
                    usersDb.usersDao().clearAll()
                val usersData = response.body()!!.map { it.toLocalOwner() }
                usersDb.usersDao().addUsers(usersData)
            }
            MediatorResult.Success(
                endOfPaginationReached = response.body()!!.isEmpty()
            )
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}