package com.example.gitusers.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.gitusers.model.LocalUsers
import com.example.gitusers.model.Users

@Dao
interface UsersDao {
    @Query("SELECT * FROM users_table")
    fun getAllUsers(): PagingSource<Int, LocalUsers>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUsers(users: List<LocalUsers>)

    @Query("DELETE FROM users_table")
    suspend fun deleteAllUsers()
}