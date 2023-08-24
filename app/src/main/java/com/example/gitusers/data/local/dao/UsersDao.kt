package com.example.gitusers.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.gitusers.model.Users

@Dao
interface UsersDao {
    @Query("SELECT * FROM users_table")
    fun getAllUsers(): PagingSource<Int, Users>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUsers(users: List<Users>)

    @Query("DELETE FROM users_table")
    suspend fun deleteAllUsers()
}