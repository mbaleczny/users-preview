package com.mbaleczny.userspreview.data.repository

import com.mbaleczny.userspreview.data.User
import com.mbaleczny.userspreview.data.remote.service.DailymotionApi
import com.mbaleczny.userspreview.data.remote.service.GitHubApi

/**
 * @author Mariusz Baleczny
 * @date 11/05/19
 */
class UserRepositoryImplementation(
    private val gitHubApi: GitHubApi,
    private val dailymotionApi: DailymotionApi
) : BaseRepository(), UserRepository {

    override suspend fun getUsers(): Resource<List<User>>? {
        val githubUsersResult =
            safeApiCall({ gitHubApi.fetchUsersAsync().await() }, "Error fetching users from github.com")

        val dailymotionUsersResult =
            safeApiCall({ dailymotionApi.fetchUsersAsync().await() }, "Error fetching users from dailymotion.com")

        val combinedResult = Resource<List<User>>(arrayListOf())

        githubUsersResult.value
            ?.map { el -> el.toUser() }
            ?.let { users ->
                combinedResult.value?.let { it as ArrayList }?.addAll(users)
            }
        combinedResult.error = combinedResult.error || githubUsersResult.error

        dailymotionUsersResult.value?.list
            ?.map { el -> el.toUser() }
            ?.let { users ->
                combinedResult.value?.let { it as ArrayList }?.addAll(users)
            }
        combinedResult.error = combinedResult.error || dailymotionUsersResult.error

        return combinedResult
    }
}