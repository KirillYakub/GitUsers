package com.example.gitusers.data

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.gitusers.data.local.dao.UsersDao
import com.example.gitusers.data.local.dao.UsersRemoteKeysDao
import com.example.gitusers.data.local.dao.UsersReposDao
import com.example.gitusers.data.local.dao.UsersReposKeysDao
import com.example.gitusers.model.LocalUsers
import com.example.gitusers.model.LocalUsersRepo
import com.example.gitusers.model.Users
import com.example.gitusers.model.UsersRemoteKeys
import com.example.gitusers.model.UsersRepoRemoteKeys

@Database(
    entities = [
        LocalUsers::class,
        UsersRemoteKeys::class,
        LocalUsersRepo::class,
        UsersRepoRemoteKeys::class],
    version = 1
)
abstract class UsersDatabase: RoomDatabase() {
    abstract fun usersDao(): UsersDao
    abstract fun usersKeysDao(): UsersRemoteKeysDao
    abstract fun usersReposDao(): UsersReposDao
    abstract fun usersReposKeysDao(): UsersReposKeysDao
}