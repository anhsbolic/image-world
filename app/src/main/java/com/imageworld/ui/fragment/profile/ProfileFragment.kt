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
import com.imageworld.ui.activity.editProfile.EditProfileActivity
import com.imageworld.ui.activity.login.LoginActivity
import com.imageworld.ui.activity.singlePost.SinglePostActivity
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
        profileMenuList.setImageResource(R.drawable.profile_menu_list_off)
        profileMenuFilter.setImageResource(R.drawable.profile_menu_filter_off)
        profileMenuSearch.setImageResource(R.drawable.profile_menu_search_off)
        profileMenuGrid.setImageResource(R.drawable.profile_menu_grid_on)
        presenter.gridView()


        //Grid View
        profileMenuGrid.setOnClickListener {
            profileMenuList.setImageResource(R.drawable.profile_menu_list_off)
            profileMenuFilter.setImageResource(R.drawable.profile_menu_filter_off)
            profileMenuSearch.setImageResource(R.drawable.profile_menu_search_off)
            profileMenuGrid.setImageResource(R.drawable.profile_menu_grid_on)
            presenter.gridView()
        }

        //Lst View
        profileMenuList.setOnClickListener {
            profileMenuGrid.setImageResource(R.drawable.profile_menu_grid_off)
            profileMenuFilter.setImageResource(R.drawable.profile_menu_filter_off)
            profileMenuSearch.setImageResource(R.drawable.profile_menu_search_off)
            profileMenuList.setImageResource(R.drawable.profile_menu_list_on)
            presenter.listView() }
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater!!.inflate(R.menu.menu_user_profile, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when(item!!.itemId) {
            R.id.menu_update->{
                val intentEditProfile = Intent(activity, EditProfileActivity::class.java)
                intentEditProfile.putExtra(EditProfileActivity.INTENT_MODE, EditProfileActivity.MODE_EDIT)
                intentEditProfile.putExtra(EditProfileActivity.INTENT_USER, userProfile)
                startActivityForResult(intentEditProfile, INTENT_EDIT)
                true
            }
            R.id.menu_logout->{
                AlertDialog.Builder(activity as DashboardActivity)
                        .setMessage(R.string.profile_menu_dialog_exit_message)
                        .setPositiveButton(R.string.profile_menu_dialog_exit_positive,{ _ , _ ->
                            presenter.logout()
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when(requestCode) {
            INTENT_EDIT -> {
                when(resultCode) {
                    INTENT_EDIT_SUCCESS -> {
                        if (data != null) {
                            val updatedProfile: UserProfile = data.getParcelableExtra(INTENT_EDIT_SUCCESS_DATA)
                            showProfile(updatedProfile)
                        }
                    }
                }
            }

            else -> {super.onActivityResult(requestCode, resultCode, data)}
        }
    }

    override fun initRecyclerView() {
        // Grid
        adapterRvPostGrid = PostGridAdapter(postList, object : PostGridAdapter.OnPhotoClickListener{
            override fun onPhotoClick(post: Post) {
                val intentSinglePost = Intent(activity, SinglePostActivity::class.java)
                intentSinglePost.putExtra(SinglePostActivity.INTENT_POST, post)
                startActivity(intentSinglePost)
            }
        })
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

    override fun showProgress() {
        if (profileProgressBar != null) {
            profileProgressBar.visibility = View.VISIBLE
        }

        if (profileRvPost != null) {
            profileRvPost.visibility = View.GONE
        }

        if (profileTxtPlaceholder != null) {
            profileTxtPlaceholder.visibility = View.GONE
        }
    }

    override fun hideProgress() {
        if (profileProgressBar != null) {
            profileProgressBar.visibility = View.GONE
        }
    }

    override fun showPlaceholder() {
        if (profileRvPost != null) {
            profileRvPost.visibility = View.GONE
        }

        if (profileTxtPlaceholder != null) {
            profileTxtPlaceholder.visibility = View.VISIBLE
        }
    }

    override fun hidePlaceholder() {
        if (profileTxtPlaceholder != null) {
            profileTxtPlaceholder.visibility = View.GONE
        }

        if (profileRvPost != null) {
            profileRvPost.visibility = View.VISIBLE
        }
    }

    override fun setGridView(postList: MutableList<Post>) {
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

        profileMenuList.requestFocus()
    }

    override fun showErrorLogout(e: String?) {
        var error = "Logout failed, please try again..."

        if (e != null) {
            error = e
        }

        Toast.makeText(activity, error, Toast.LENGTH_LONG).show()
    }

    override fun goToLogin() {
        val intentLogin = Intent(activity, LoginActivity::class.java)
        startActivity(intentLogin)
        activity!!.finish()
    }

    companion object {

        const val INTENT_EDIT : Int = 20
        const val INTENT_EDIT_SUCCESS : Int = 21
        const val INTENT_EDIT_SUCCESS_DATA = "UpdatedProfileData"


        @JvmStatic
        fun newInstance() =
                ProfileFragment().apply {
                    arguments = Bundle().apply {}
                }
    }
}
