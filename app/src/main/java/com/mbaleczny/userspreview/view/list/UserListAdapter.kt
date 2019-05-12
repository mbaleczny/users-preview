package com.mbaleczny.userspreview.view.list

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mbaleczny.userspreview.R
import com.mbaleczny.userspreview.data.User
import com.mbaleczny.userspreview.databinding.ViewHolderUserBinding

/**
 * @author Mariusz Baleczny
 * @date 11/05/19
 */
class UserListAdapter(private val clickListener: (User, ImageView, Int) -> Unit) :
    ListAdapter<User, UserListAdapter.ViewHolder>(UserDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val binding = DataBindingUtil
            .inflate<ViewHolderUserBinding>(inflater, R.layout.view_holder_user, parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), position, clickListener)
    }

    class ViewHolder(
        private val binding: ViewHolderUserBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(
            user: User,
            position: Int,
            clickListener: (User, ImageView, Int) -> Unit
        ) {
            binding.user = user
            itemView.setOnClickListener { clickListener.invoke(user, binding.avatar, position) }
        }
    }
}