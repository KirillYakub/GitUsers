package com.example.gitusers.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.gitusers.data.local.LocalOwner
import com.example.gitusers.data.local.connect.OwnerDao

@Database(entities = [LocalOwner::class], version = 1)
abstract class OwnerDatabase : RoomDatabase() {
    abstract fun ownerDao(): OwnerDao
}