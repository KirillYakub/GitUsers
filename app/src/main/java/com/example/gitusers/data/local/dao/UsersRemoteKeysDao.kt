package com.example.gitusers.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.gitusers.model.UsersRemoteKeys

@Dao
interface UsersRemoteKeysDao {
    @Query("SELECT * FROM unsplash_keys_table WHERE id =:id")
    suspend fun getRemoteKeys(id: Int): UsersRemoteKeys

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllRemoteKeys(remoteKeys: List<UsersRemoteKeys>)

    @Query("DELETE FROM unsplash_keys_table")
    suspend fun deleteAllKeys()
}