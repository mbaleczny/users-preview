package com.mbaleczny.userspreview.data.repository

import android.util.Log
import retrofit2.Response
import java.io.IOException

open class BaseRepository {

    suspend fun <T : Any> safeApiCall(call: suspend () -> Response<T>, errorMessage: String): Resource<T> {
        val result: Result<T> = safeApiResult(call, errorMessage)
        val data: Resource<T> = Resource(null)

        when (result) {
            is Result.Success ->
                data.value = result.data
            is Result.Error -> {
                Log.d("BaseRepository", "${result.exception} - $errorMessage")
                data.error = true
            }
        }

        return data
    }

    private suspend fun <T : Any> safeApiResult(call: suspend () -> Response<T>, errorMessage: String): Result<T> {
        val response = call.invoke()
        if (response.isSuccessful) return Result.Success(response.body()!!)
        return Result.Error(IOException("Error while network call - $errorMessage"))
    }
}
