package com.mbaleczny.userspreview.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

/**
 * @author Mariusz Baleczny
 * @date 13/05/19
 */
class TestObserver<T> : Observer<T> {

    val observedValues = mutableListOf<T>()

    override fun onChanged(value: T) {
        observedValues.add(value)
    }
}

fun <T> LiveData<T>.testObserver() = TestObserver<T>().also { observeForever(it) }
