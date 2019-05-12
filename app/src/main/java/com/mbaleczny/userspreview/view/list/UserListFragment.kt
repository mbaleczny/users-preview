package com.mbaleczny.userspreview.view.list

import android.app.Activity
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.app.ActivityOptionsCompat
import androidx.core.app.SharedElementCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.mbaleczny.userspreview.R
import com.mbaleczny.userspreview.data.User
import com.mbaleczny.userspreview.view.detail.UserDetailActivity
import com.mbaleczny.userspreview.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_user_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * @author Mariusz Baleczny
 * @date 11/05/19
 */
class UserListFragment : Fragment() {

    private val userViewModel: UserViewModel by viewModel()

    private lateinit var adapter: UserListAdapter
    private lateinit var recycler: RecyclerView

    private var viewHolderPosition: Int? = null

    /**
     * Reassigns view for exit transition for recreated activity.
     */
    private val sharedElementCallback =
        object : SharedElementCallback() {
            override fun onMapSharedElements(
                names: MutableList<String>?,
                sharedElements: MutableMap<String, View>?
            ) {
                super.onMapSharedElements(names, sharedElements)
                viewHolderPosition?.let { position ->
                    if (sharedElements?.isEmpty() == true) {
                        recycler.layoutManager
                            ?.findViewByPosition(position)
                            ?.findViewById<View>(R.id.avatar)
                            ?.let { view ->
                                names?.get(0)?.let { sharedElements.put(it, view) }
                            }
                    }
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        savedInstanceState?.getInt(VIEW_HOLDER_POSITION)?.let {
            viewHolderPosition = it
            activity?.setExitSharedElementCallback(sharedElementCallback)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_user_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler = view.findViewById(R.id.user_list_recycler)
        adapter = UserListAdapter(this::openUserDetailActivity)

        recycler.layoutManager = createLayoutManager(activity)
        recycler.itemAnimator = DefaultItemAnimator()
        recycler.adapter = adapter

        activity?.let {
            userViewModel.isLoading().observe(this, Observer { isLoading ->
                fragment_user_list_refresh_layout.isRefreshing = isLoading
            })

            userViewModel.isError().observe(this, Observer { isError ->
                if (isError) {
                    Snackbar.make(view, R.string.network_error_message, Snackbar.LENGTH_SHORT).show()
                }
            })

            userViewModel.getUsers().observe(this, Observer(adapter::submitList))
        }

        fragment_user_list_refresh_layout.setOnRefreshListener { userViewModel.loadUsers() }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        viewHolderPosition?.let { outState.putInt(VIEW_HOLDER_POSITION, it) }
        super.onSaveInstanceState(outState)
    }

    private fun createLayoutManager(activity: Activity?): RecyclerView.LayoutManager {
        return activity?.resources?.configuration?.let {
            if (it.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                GridLayoutManager(activity, GRID_VIEW_COLUMNS)
            } else {
                LinearLayoutManager(context)
            }
        } ?: LinearLayoutManager(context)
    }

    private fun openUserDetailActivity(user: User, avatarImageView: ImageView, position: Int) {
        viewHolderPosition = position
        Intent(requireActivity(), UserDetailActivity::class.java)
            .apply {
                putExtra(UserDetailActivity.EXTRA_USER, user)
                val options = ActivityOptionsCompat
                    .makeSceneTransitionAnimation(
                        activity as Activity, avatarImageView, avatarImageView.transitionName
                    )
                startActivity(this, options.toBundle())
            }
    }

    companion object {

        const val GRID_VIEW_COLUMNS = 2
        const val VIEW_HOLDER_POSITION = "view_position"

        fun newInstance(): UserListFragment = UserListFragment()
    }
}
