package com.example.gitusers.di

import android.content.Context
import androidx.room.Room
import com.example.gitusers.data.UsersDatabase
import com.example.gitusers.util.DatabaseData
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): UsersDatabase {
        return Room.databaseBuilder(
            context,
            UsersDatabase::class.java,
            DatabaseData.databaseName
        ).build()
    }
}