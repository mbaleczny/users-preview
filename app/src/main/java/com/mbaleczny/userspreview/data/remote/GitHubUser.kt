package com.mbaleczny.userspreview.data.remote

import com.mbaleczny.userspreview.data.User
import com.squareup.moshi.Json

/**
 * @author Mariusz Baleczny
 * @date 11/05/19
 */
data class GitHubUser(
    @Json(name = "login") val login: String,
    @Json(name = "avatar_url") val avatarUrl: String
) {
    fun toUser(): User =
        User(login, avatarUrl, "github.com")
}

