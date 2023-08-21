package com.example.gitusers.common

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.room.Room
import com.example.gitusers.data.local.LocalOwner
import com.example.gitusers.data.local.db.OwnerDatabase
import com.example.gitusers.data.local.mediator.MediatorCallback
import com.example.gitusers.data.local.mediator.OwnersMediator
import com.example.gitusers.data.remote.connect.OwnersRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppInstance {

    @Provides
    @Singleton
    fun providesUsersDatabase(@ApplicationContext context: Context): OwnerDatabase {
        return Room.databaseBuilder(
            context,
            OwnerDatabase::class.java, "user_database.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideUsersApi(): OwnersRepository {
        val interceptor = HttpLoggingInterceptor().also {
            it.level = HttpLoggingInterceptor.Level.BODY
        }
        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
        return Retrofit.Builder()
            .baseUrl("https://api.github.com")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(OwnersRepository::class.java)
    }

    @OptIn(ExperimentalPagingApi::class)
    @Provides
    @Singleton
    fun provideUsersPager(
        callback: MediatorCallback,
        database: OwnerDatabase,
        api: OwnersRepository
    ): Pager<Int, LocalOwner> {
        return Pager(
            config = PagingConfig(5),
            remoteMediator = OwnersMediator(
                callback, database, api
            ),
            pagingSourceFactory = {
                database.ownerDao().pagingSource()
            }
        )
    }

    @Provides
    @Singleton
    fun createMediatorCallback(): MediatorCallback {
        return object : MediatorCallback {
            override fun updateState(resource: Resource<List<LocalOwner>>) {
                // Implement the updateState function
            }
        }
    }
}