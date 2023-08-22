package com.example.gitusers.data.local.connect

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.gitusers.data.local.LocalUser
import kotlinx.coroutines.flow.Flow

@Dao
interface UsersDao {
    @Upsert
    suspend fun addUsers(list: List<LocalUser>)

    @Query("DELETE FROM users")
    suspend fun clearAll()

    @Query("SELECT * FROM users")
    fun pagingSource(): PagingSource<Int, LocalUser>
}