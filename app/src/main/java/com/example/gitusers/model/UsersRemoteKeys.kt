package com.example.gitusers.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.gitusers.util.DatabaseData

@Entity(tableName = DatabaseData.usersKeysTable)
data class UsersRemoteKeys(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val prevPage: Int?,
    val nextPage: Int?
)
