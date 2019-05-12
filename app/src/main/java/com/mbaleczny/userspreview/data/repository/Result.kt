package com.mbaleczny.userspreview.data.repository

/**
 * @author Mariusz Baleczny
 * @date 11/05/19
 */
sealed class Result<out T : Any> {
    data class Success<out T : Any>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
}