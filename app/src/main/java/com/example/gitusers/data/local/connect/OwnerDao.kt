package com.example.gitusers.data.local.connect

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.example.gitusers.data.local.LocalOwner
import kotlinx.coroutines.flow.Flow

@Dao
interface OwnerDao {
    @Upsert
    suspend fun addUsers(list: List<LocalOwner>)

    @Query("SELECT * FROM users")
    fun pagingSource(): PagingSource<Int, LocalOwner>

    @Query("SELECT * FROM users")
    fun readData(): Flow<List<LocalOwner>>
}