package com.mbaleczny.userspreview.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * @author Mariusz Baleczny
 * @date 10/05/19
 */
@Parcelize
data class User(
    val username: String,
    val imageUrl: String,
    val source: String
) : Parcelable