package com.example.gitusers.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.gitusers.data.UsersDatabase
import com.example.gitusers.data.remote.UsersApi
import com.example.gitusers.model.LocalUsers
import com.example.gitusers.model.UsersRemoteKeys
import com.example.gitusers.model.toLocalUsers
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class UsersMediator @Inject constructor(
    private val usersApi: UsersApi,
    private val usersDatabase: UsersDatabase
): RemoteMediator<Int, LocalUsers>() {
    private val usersDao = usersDatabase.usersDao()
    private val usersKeysDao = usersDatabase.usersKeysDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, LocalUsers>
    ): MediatorResult {
        return try {
            val currentPage = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextPage?.minus(1) ?: 1
                }
                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    val prevPage: Int = remoteKeys?.prevPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    prevPage
                }
                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextPage: Int = remoteKeys?.nextPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    nextPage
                }
            }

            val response = usersApi.getUsers(
                since = currentPage,
                perPage = 10,
            )

            val endOfPaginationReached = response.isEmpty()
            val prevPage = if (currentPage == 1) null else currentPage - 1
            val nextPage = if (endOfPaginationReached) null else currentPage + 1

            usersDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    usersDao.deleteAllUsers()
                    usersKeysDao.deleteAllKeys()
                }
                val keys = response.map {users ->
                    UsersRemoteKeys(
                        id = users.id,
                        prevPage = prevPage,
                        nextPage = nextPage
                    )
                }
                usersKeysDao.addAllRemoteKeys(remoteKeys = keys)
                usersDao.addUsers(users = response.map { it.toLocalUsers() })
            }
            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, LocalUsers>
    ): UsersRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                usersKeysDao.getRemoteKeys(id = id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, LocalUsers>
    ): UsersRemoteKeys? {
        return state.pages
            .firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { users ->
                usersKeysDao.getRemoteKeys(id = users.id)
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, LocalUsers>
    ): UsersRemoteKeys? {
        return state.pages
            .lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { users ->
                usersKeysDao.getRemoteKeys(id = users.id)
            }
    }
}