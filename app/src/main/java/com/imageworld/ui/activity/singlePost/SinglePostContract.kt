package com.imageworld.ui.activity.singlePost

import com.imageworld.model.Post

interface SinglePostContract {

    interface View {
        fun updateUi(post: Post)
    }

    interface Presenter {
        fun setPostData(post: Post)
    }

}