package com.example.gitusers.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.gitusers.data.local.LocalUser
import com.example.gitusers.data.local.connect.UsersDao

@Database(entities = [LocalUser::class], version = 1)
abstract class UsersDatabase : RoomDatabase() {
    abstract fun usersDao(): UsersDao
}