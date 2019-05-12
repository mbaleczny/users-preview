package com.mbaleczny.userspreview.data.remote.service

import com.mbaleczny.userspreview.data.remote.DailymotionResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @author Mariusz Baleczny
 * @date 10/05/19
 */
interface DailymotionApi {

    companion object {
        val DEFAULT_FIELDS = listOf("avatar_360_url", "username").joinToString(separator = ",")
    }

    @GET("users")
    fun fetchUsersAsync(
        @Query("fields") fields: String = DEFAULT_FIELDS
    ): Deferred<Response<DailymotionResponse>>

}