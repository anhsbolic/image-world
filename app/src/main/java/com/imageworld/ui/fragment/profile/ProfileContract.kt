package com.imageworld.ui.fragment.profile

import com.imageworld.model.Post
import com.imageworld.model.UserProfile

interface ProfileContract {

    interface View {

        fun initRecyclerView()

        fun showProfile(profile: UserProfile)

        fun showPlaceholder()

        fun hidePlaceholder()

        fun setGridView(postList: MutableList<Post>)

        fun setListView(postList: MutableList<Post>)

        fun showErrorLogout(e: String?)

        fun goToLogin()
    }

    interface Presenter {
        fun getProfile()

        fun gridView()

        fun listView()

        fun logout()
    }

}