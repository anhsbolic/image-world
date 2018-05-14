package com.imageworld.ui.fragment.home

import com.imageworld.model.Post

interface HomeContract {
    interface View {
        fun initRecyclerView()

        fun showProgress()

        fun hideProgress()

        fun showPlaceholder()

        fun hidePlaceholder()

        fun showPostList(postList: MutableList<Post>)
    }

    interface Presenter {
        fun loadPostListData()
    }
}