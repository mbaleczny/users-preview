package com.mbaleczny.userspreview.data.remote

import com.mbaleczny.userspreview.data.User

/**
 * @author Mariusz Baleczny
 * @date 11/05/19
 */
data class DailymotionUser(
    val username: String,
    val avatar_360_url: String
) {
    fun toUser(): User =
        User(username, avatar_360_url, "dailymotion.com")
}

