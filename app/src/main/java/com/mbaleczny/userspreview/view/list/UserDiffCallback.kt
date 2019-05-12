package com.mbaleczny.userspreview.view.list

import androidx.recyclerview.widget.DiffUtil
import com.mbaleczny.userspreview.data.User

/**
 * @author Mariusz Baleczny
 * @date 11/05/19
 */
class UserDiffCallback : DiffUtil.ItemCallback<User>() {

    override fun areItemsTheSame(oldItem: User, newItem: User) =
        oldItem.username == newItem.username

    override fun areContentsTheSame(oldItem: User, newItem: User) =
        oldItem == newItem
}