package com.mbaleczny.userspreview.util

import kotlinx.coroutines.CoroutineDispatcher

/**
 * @author Mariusz Baleczny
 * @date 15/05/19
 */
data class CoroutinesDispatcherProvider(
    val main: CoroutineDispatcher,
    val computation: CoroutineDispatcher,
    val io: CoroutineDispatcher
)