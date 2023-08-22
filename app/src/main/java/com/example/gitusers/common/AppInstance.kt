package com.example.gitusers.common

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.room.Room
import com.example.gitusers.common.gitdata.remote.GitHubClient
import com.example.gitusers.data.local.LocalOwner
import com.example.gitusers.data.local.db.OwnerDatabase
import com.example.gitusers.data.local.mediator.OwnersMediator
import com.example.gitusers.data.remote.connect.OwnersRepository
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
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
        return RetrofitInstance.retrofit.create(OwnersRepository::class.java)
    }

    @OptIn(ExperimentalPagingApi::class)
    @Provides
    @Singleton
    fun provideUsersPager(
        database: OwnerDatabase,
        api: OwnersRepository
    ): Pager<Int, LocalOwner> {
        return Pager(
            config = PagingConfig(10),
            remoteMediator = OwnersMediator(
                ownerDb = database,
                gitApi = api
            ),
            pagingSourceFactory = {
                database.ownerDao().pagingSource()
            }
        )
    }
}

object RetrofitInstance {
    private val interceptor = HttpLoggingInterceptor().also {
        it.level = HttpLoggingInterceptor.Level.BODY
    }
    private val client = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .build()
    private var gson: Gson = GsonBuilder()
        .setLenient()
        .create()
    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://api.github.com")
        .client(client)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
    val retrofitGit: Retrofit = Retrofit.Builder()
        .baseUrl("https://github.com")
        .client(client)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
    val gitInstance: GitHubClient = retrofitGit.create(GitHubClient::class.java)
}