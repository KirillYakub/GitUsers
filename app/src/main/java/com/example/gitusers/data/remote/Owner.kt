package com.example.gitusers.data.remote

import com.example.gitusers.data.local.LocalOwner
import com.google.gson.annotations.SerializedName

data class Owner(
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

fun Owner.toLocalOwner(): LocalOwner {
    return LocalOwner(
        avatarUrl = this.avatarUrl,
        eventsUrl = this.eventsUrl,
        followersUrl = this.followersUrl,
        followingUrl = this.followingUrl,
        gistsUrl = this.gistsUrl,
        gravatarId = this.gravatarId,
        htmlUrl = this.htmlUrl,
        id = this.id,
        login = this.login,
        nodeId = this.nodeId,
        organizationsUrl = this.organizationsUrl,
        receivedEventsUrl = this.receivedEventsUrl,
        reposUrl = this.reposUrl,
        siteAdmin = this.siteAdmin,
        starredUrl = this.starredUrl,
        subscriptionsUrl = this.subscriptionsUrl,
        type = this.type,
        url = this.url
    )
}