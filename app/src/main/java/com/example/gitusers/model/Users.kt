package com.example.gitusers.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.gitusers.util.DatabaseData
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class Users(
    @SerializedName("avatar_url")
    val avatarUrl: String,
    @SerializedName("events_url")
    val eventsUrl: String,
    @SerializedName("followers_url")
    val followersUrl: String,
    @SerializedName("following_url")
    val followingUrl: String,
    @SerializedName("gists_url")
    val gistsUrl: String,
    @SerializedName("gravatar_id")
    val gravatarId: String,
    @SerializedName("html_url")
    val htmlUrl: String,
    val id: Int,
    val login: String,
    @SerializedName("node_id")
    val nodeId: String,
    @SerializedName("organizations_url")
    val organizationsUrl: String,
    @SerializedName("received_events_url")
    val receivedEventsUrl: String,
    @SerializedName("repos_url")
    val reposUrl: String,
    @SerializedName("site_admin")
    val siteAdmin: Boolean,
    @SerializedName("starred_url")
    val starredUrl: String,
    @SerializedName("subscriptions_url")
    val subscriptionsUrl: String,
    val type: String,
    val url: String
)

@Entity(tableName = DatabaseData.usersTable)
data class LocalUsers(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val avatarUrl: String,
    val login: String,
)

fun Users.toLocalUsers(): LocalUsers {
    return LocalUsers(
        id = id,
        avatarUrl = avatarUrl,
        login = login
    )
}