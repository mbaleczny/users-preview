package com.mbaleczny.userspreview.data.remote.service

import com.mbaleczny.userspreview.data.remote.GitHubUser
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET

/**
 * @author Mariusz Baleczny
 * @date 10/05/19
 */
interface GitHubApi {

    @GET("users")
    fun fetchUsersAsync(): Deferred<Response<List<GitHubUser>>>
}