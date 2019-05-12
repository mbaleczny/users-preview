package com.mbaleczny.userspreview.data.repository

/**
 * @author Mariusz Baleczny
 * @date 12/05/19
 */
class Resource<T : Any>(var value: T?, var error: Boolean = false)