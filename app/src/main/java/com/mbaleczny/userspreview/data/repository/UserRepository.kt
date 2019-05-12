package com.mbaleczny.userspreview.data.repository

import com.mbaleczny.userspreview.data.User
import com.mbaleczny.userspreview.data.remote.DailymotionResponse
import com.mbaleczny.userspreview.data.remote.GitHubUser
import com.mbaleczny.userspreview.data.remote.service.ApiFactory

/**
 * @author Mariusz Baleczny
 * @date 11/05/19
 */
class UserRepository : BaseRepository() {

    private val gitHubApi = ApiFactory.github
    private val dailymotionApi = ApiFactory.dailymotion

    suspend fun getUsers(): Resource<List<User>>? {
        val githubUsersResult: Resource<List<GitHubUser>> =
            safeApiCall({ gitHubApi.fetchUsersAsync().await() }, "Error fetching users from github.com")

        val dailymotionUsersResult: Resource<DailymotionResponse> =
            safeApiCall({ dailymotionApi.fetchUsersAsync().await() }, "Error fetching users from dailymotion.com")

        val combinedResult = Resource<List<User>>(arrayListOf())

        githubUsersResult.value
            ?.map { el -> el.toUser() }
            ?.let { users -> (combinedResult.value as ArrayList).addAll(users) }
        combinedResult.error = combinedResult.error || githubUsersResult.error

        dailymotionUsersResult.value?.list
            ?.map { el -> el.toUser() }
            ?.let { users -> (combinedResult.value as ArrayList).addAll(users) }
        combinedResult.error = combinedResult.error || dailymotionUsersResult.error

        return combinedResult
    }
}