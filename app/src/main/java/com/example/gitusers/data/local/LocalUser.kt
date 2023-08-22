package com.example.gitusers.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class LocalUser(
    val avatarUrl: String,
    val eventsUrl: String,
    val followersUrl: String,
    val followingUrl: String,
    val gistsUrl: String,
    val gravatarId: String,
    val htmlUrl: String,
    @PrimaryKey
    val id: Int,
    val login: String,
    val nodeId: String,
    val organizationsUrl: String,
    val receivedEventsUrl: String,
    val reposUrl: String,
    val siteAdmin: Boolean,
    val starredUrl: String,
    val subscriptionsUrl: String,
    val type: String,
    val url: String
)

data class UserDisplayData(
    val id: Int,
    val login: String,
    val reposUrl: String,
    val avatarUrl: String,
)

fun LocalUser.toUserDisplay(): UserDisplayData {
    return UserDisplayData(
        id = id,
        login = login,
        reposUrl = reposUrl,
        avatarUrl = avatarUrl
    )
}