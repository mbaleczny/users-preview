package com.mbaleczny.userspreview.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.mbaleczny.userspreview.R


/**
 * @author Mariusz Baleczny
 * @date 11/05/19
 */

@BindingAdapter("avatarImage")
fun ImageView.loadImage(url: String?) {
    Glide.with(this.context)
        .load(url)
        .error(R.drawable.ic_person)
        .into(this)
}