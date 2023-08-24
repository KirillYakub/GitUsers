package com.example.gitusers.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.gitusers.data.local.dao.UsersDao
import com.example.gitusers.data.local.dao.UsersRemoteKeysDao
import com.example.gitusers.model.Users
import com.example.gitusers.model.UsersRemoteKeys

@Database(
    entities = [Users::class, UsersRemoteKeys::class],
    version = 1
)
abstract class UsersDatabase: RoomDatabase() {
    abstract fun usersDao(): UsersDao
    abstract fun usersKeysDao(): UsersRemoteKeysDao
}