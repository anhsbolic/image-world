package com.imageworld.ui.fragment.home

import com.imageworld.model.Post

interface HomeContract {
    interface View {
        fun initRecyclerView()

        fun showPostList(postList: MutableList<Post>)
    }

    interface Presenter {
        fun loadPostListData()
    }
}