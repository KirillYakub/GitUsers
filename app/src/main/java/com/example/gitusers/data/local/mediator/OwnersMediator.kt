package com.example.gitusers.data.local.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.gitusers.common.Resource
import com.example.gitusers.data.local.LocalOwner
import com.example.gitusers.data.local.db.OwnerDatabase
import com.example.gitusers.data.remote.connect.OwnersRepository
import com.example.gitusers.data.remote.toLocalOwner
import kotlinx.coroutines.flow.first

@OptIn(ExperimentalPagingApi::class)
class OwnersMediator(
    private val mediatorCallback: MediatorCallback,
    private val ownerDb: OwnerDatabase,
    private val gitApi: OwnersRepository
) : RemoteMediator<Int, LocalOwner>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, LocalOwner>
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
            mediatorCallback.updateState(Resource.Loading())
            val response = gitApi.getUsers(
                page = loadKey,
                pageCount = state.config.pageSize,
                "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"
            )
            if (response.isSuccessful) {
                val owners = response.body() ?: emptyList()
                ownerDb.withTransaction {
                    val ownerEntry = owners.map { it.toLocalOwner() }
                    ownerDb.ownerDao().addUsers(ownerEntry)
                    mediatorCallback.updateState(Resource.Success(ownerEntry))
                }
                MediatorResult.Success(
                    endOfPaginationReached = owners.isEmpty()
                )
            } else {
                val data = ownerDb.ownerDao().readData().first()
                mediatorCallback.updateState(Resource.Success(data))
                MediatorResult.Success(
                    endOfPaginationReached = data.isEmpty()
                )
            }
        } catch (e: Exception) {
            mediatorCallback.updateState(Resource.Error(message = "Something went wrong.."))
            MediatorResult.Error(e)
        }
    }
}