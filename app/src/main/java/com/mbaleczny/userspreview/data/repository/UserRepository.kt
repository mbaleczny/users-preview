package com.mbaleczny.userspreview.data.repository

import com.mbaleczny.userspreview.data.User

interface UserRepository {
    suspend fun getUsers(): Resource<List<User>>?
}