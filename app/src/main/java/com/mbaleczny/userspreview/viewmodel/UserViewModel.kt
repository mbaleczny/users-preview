package com.mbaleczny.userspreview.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mbaleczny.userspreview.data.User
import com.mbaleczny.userspreview.data.repository.UserRepository
import kotlinx.coroutines.launch

/**
 * @author Mariusz Baleczny
 * @date 11/05/19
 */
class UserViewModel(private val repository: UserRepository) : ViewModel() {

    private lateinit var _users: MutableLiveData<List<User>>

    fun loadUsers() {
        viewModelScope.launch {
            val result = repository.getUsers()
            _isLoading.value = false
            _isError.value = result?.error
            _users.value = result?.value ?: listOf()
        }
    }

    fun getUsers(): LiveData<List<User>> {
        if (!::_users.isInitialized) {
            _users = MutableLiveData()
            loadUsers()
        }
        return _users
    }

    private lateinit var _isLoading: MutableLiveData<Boolean>

    fun isLoading(): LiveData<Boolean> {
        if (!::_isLoading.isInitialized) {
            _isLoading = MutableLiveData()
            _isLoading.value = true
        }
        return _isLoading
    }

    private lateinit var _isError: MutableLiveData<Boolean>

    fun isError(): LiveData<Boolean> {
        if (!::_isError.isInitialized) {
            _isError = MutableLiveData()
            _isError.value = false
        }
        return _isError
    }
}