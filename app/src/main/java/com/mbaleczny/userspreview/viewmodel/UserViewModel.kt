package com.mbaleczny.userspreview.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mbaleczny.userspreview.data.User
import com.mbaleczny.userspreview.data.repository.UserRepository
import com.mbaleczny.userspreview.util.CoroutinesDispatcherProvider
import kotlinx.coroutines.launch

/**
 * @author Mariusz Baleczny
 * @date 11/05/19
 */
class UserViewModel(
    private val repository: UserRepository,
    private val dispatcherProvider: CoroutinesDispatcherProvider
) : ViewModel() {

    private val _users by lazy { MutableLiveData<List<User>>().also { loadUsers() } }
    private val _isLoading by lazy { MutableLiveData<Boolean>().apply { value = true } }
    private val _isError by lazy { MutableLiveData<Boolean>().apply { value = false } }

    fun getUsers(): LiveData<List<User>> = _users
    fun isLoading(): LiveData<Boolean> = _isLoading
    fun isError(): LiveData<Boolean> = _isError

    fun loadUsers() {
        viewModelScope.launch(dispatcherProvider.io) {
            val result = repository.getUsers()
            _isLoading.postValue(false)
            _isError.postValue(result?.error)
            _users.postValue(result?.value ?: listOf())
        }
    }
}