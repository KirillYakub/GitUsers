package com.example.gitusers.common

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.room.Room
import com.example.gitusers.common.gitdata.remote.GitHubClient
import com.example.gitusers.data.local.LocalUser
import com.example.gitusers.data.UsersDatabase
import com.example.gitusers.data.local.UsersMediator
import com.example.gitusers.data.remote.connect.UsersRepository
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
    fun providesUsersDatabase(@ApplicationContext context: Context): UsersDatabase {
        return Room.databaseBuilder(
            context,
            UsersDatabase::class.java, "user_database.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideUsersApi(): UsersRepository {
        return RetrofitInstance.retrofit.create(UsersRepository::class.java)
    }

    @OptIn(ExperimentalPagingApi::class)
    @Provides
    @Singleton
    fun provideUsersPager(
        database: UsersDatabase,
        api: UsersRepository
    ): Pager<Int, LocalUser> {
        return Pager(
            config = PagingConfig(20),
            remoteMediator = UsersMediator(
                usersDb = database,
                gitApi = api
            ),
            pagingSourceFactory = {
                database.usersDao().pagingSource()
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
    private val retrofitGit: Retrofit = Retrofit.Builder()
        .baseUrl("https://github.com")
        .client(client)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://api.github.com")
        .client(client)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
    val gitClient: GitHubClient = retrofitGit.create(GitHubClient::class.java)
}

/*
object InternetConnection {
    fun isInternetAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkCapabilities = connectivityManager.activeNetwork ?: return false
        val actNw =
            connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
        return when {
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }
}*/