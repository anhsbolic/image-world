package com.imageworld.ui.fragment.profile

import com.imageworld.model.Post
import com.imageworld.model.UserProfile

interface ProfileContract {

    interface View {
        fun showProfile(profile: UserProfile)

        fun setGridView(postList: MutableList<Post>)

        fun setListView(postList: MutableList<Post>)

        fun goToLogin()
    }

    interface Presenter {
        fun getProfile()

        fun gridView()

        fun listView()

        fun logout()
    }

}