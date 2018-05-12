package com.imageworld.ui.fragment.profile

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.Toast
import com.bumptech.glide.Glide
import com.imageworld.R
import com.imageworld.helper.GridSpacingItemDecoration
import com.imageworld.model.Post
import com.imageworld.model.UserProfile
import com.imageworld.ui.activity.dashboard.DashboardActivity
import com.imageworld.ui.activity.login.LoginActivity
import com.imageworld.ui.adapter.PostGridAdapter
import com.imageworld.ui.adapter.PostListAdapter
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : Fragment(), ProfileContract.View {

    private lateinit var presenter: ProfilePresenter
    private lateinit var userProfile : UserProfile
    private var postList: MutableList<Post> = ArrayList()
    private lateinit var adapterRvPostList: RecyclerView.Adapter<*>
    private lateinit var layoutManagerRvPostList: RecyclerView.LayoutManager
    private lateinit var adapterRvPostGrid: RecyclerView.Adapter<*>
    private lateinit var layoutManagerRvPostGrid: RecyclerView.LayoutManager
    private lateinit var gridItemDecoration: GridSpacingItemDecoration

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Init Presenter
        presenter = ProfilePresenter(this@ProfileFragment)

        //Get UserProfile
        presenter.getProfile()

        //Init RecyclerView
        initRecyclerView()

        //Init page with grid layout
        presenter.gridView()

        //Grid View
        profileMenuGrid.setOnClickListener { presenter.gridView() }

        //Lst View
        profileMenuList.setOnClickListener { presenter.listView() }
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater!!.inflate(R.menu.menu_user_profile, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when(item!!.itemId) {
            R.id.menu_update->{
                Toast.makeText(activity,"UPDATE",Toast.LENGTH_SHORT).show()
                true
            }
            R.id.menu_settings->{
                Toast.makeText(activity,"SETTINGS",Toast.LENGTH_SHORT).show()
                true
            }
            R.id.menu_logout->{
                AlertDialog.Builder(activity as DashboardActivity)
                        .setMessage(R.string.profile_menu_dialog_exit_message)
                        .setPositiveButton(R.string.profile_menu_dialog_exit_positive,{ _ , _ ->
                            presenter.logout(activity as DashboardActivity)
                        })
                        .setNegativeButton(R.string.profile_menu_dialog_exit_negative, null)
                        .show()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    override fun initRecyclerView() {
        // Grid
        adapterRvPostGrid = PostGridAdapter(postList)
        layoutManagerRvPostGrid = GridLayoutManager(activity,3)
        val spacingInPixels = resources.getDimensionPixelSize(R.dimen.grid_layout_margin)
        gridItemDecoration = GridSpacingItemDecoration(3,
                spacingInPixels, true, 0)

        //List
        adapterRvPostList = PostListAdapter(postList)
        layoutManagerRvPostList = LinearLayoutManager(activity)

    }

    override fun showProfile(profile: UserProfile) {
        userProfile = profile

        val imgProfile = userProfile.imageProfile
        val username = userProfile.username
        val bio = userProfile.bio

        Glide.with(this@ProfileFragment).load(imgProfile).into(profileImg)
        profileTxtUsername.text = username
        profileTxtBio.text = bio
    }

    override fun setGridView(postList: MutableList<Post>) {
        profileMenuList.setImageResource(R.drawable.profile_menu_list_off)
        profileMenuFilter.setImageResource(R.drawable.profile_menu_filter_off)
        profileMenuSearch.setImageResource(R.drawable.profile_menu_search_off)
        profileMenuGrid.setImageResource(R.drawable.profile_menu_grid_on)

        profileRvPost.layoutManager = layoutManagerRvPostGrid
        profileRvPost.addItemDecoration(gridItemDecoration)
        profileRvPost.adapter = adapterRvPostGrid


        if (profileRvPost != null) {
            if (this.postList.isNotEmpty()) {
                this.postList.clear()
            }

            for (i in 0 until postList.size) {
                this.postList.add(postList[i])
                val lastIndex = this.postList.lastIndex
                adapterRvPostGrid.notifyItemInserted(lastIndex)
            }
        }
    }

    override fun setListView(postList: MutableList<Post>) {
        profileMenuGrid.setImageResource(R.drawable.profile_menu_grid_off)
        profileMenuFilter.setImageResource(R.drawable.profile_menu_filter_off)
        profileMenuSearch.setImageResource(R.drawable.profile_menu_search_off)
        profileMenuList.setImageResource(R.drawable.profile_menu_list_on)

        profileRvPost.adapter = adapterRvPostList
        profileRvPost.layoutManager = layoutManagerRvPostList
        profileRvPost.removeItemDecoration(gridItemDecoration)

        if (profileRvPost != null) {
            if (this.postList.isNotEmpty()) {
                this.postList.clear()
            }

            for (i in 0 until postList.size) {
                this.postList.add(postList[i])
                val lastIndex = this.postList.lastIndex
                adapterRvPostList.notifyItemInserted(lastIndex)
            }
        }
    }

    override fun goToLogin() {
        val intentLogin = Intent(activity, LoginActivity::class.java)
        startActivity(intentLogin)
        activity!!.finish()
    }

    companion object {

        @JvmStatic
        fun newInstance() =
                ProfileFragment().apply {
                    arguments = Bundle().apply {}
                }
    }
}
