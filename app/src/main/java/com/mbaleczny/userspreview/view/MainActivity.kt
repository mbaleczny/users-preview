package com.mbaleczny.userspreview.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mbaleczny.userspreview.R
import com.mbaleczny.userspreview.view.list.UserListFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.container, UserListFragment.newInstance())
                .commit()
        }
    }
}
