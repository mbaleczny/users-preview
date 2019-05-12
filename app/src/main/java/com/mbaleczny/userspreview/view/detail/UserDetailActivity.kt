package com.mbaleczny.userspreview.view.detail

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.mbaleczny.userspreview.R
import com.mbaleczny.userspreview.data.User
import com.mbaleczny.userspreview.databinding.ActivityUserDetailBinding

class UserDetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_USER = "user"
    }

    private lateinit var binding: ActivityUserDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_detail)

        supportActionBar?.apply { setDisplayHomeAsUpEnabled(true) }

        when (savedInstanceState) {
            null ->
                intent
                    .takeIf { it.hasExtra(EXTRA_USER) }
                    ?.apply {
                        binding.user = getParcelableExtra(EXTRA_USER)
                    }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                supportFinishAfterTransition()
                true
            }
            else ->
                super.onOptionsItemSelected(item)
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        savedInstanceState?.getParcelable<User>(EXTRA_USER)
            ?.let { binding.user = it }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelable(EXTRA_USER, binding.user)
        super.onSaveInstanceState(outState)
    }
}
