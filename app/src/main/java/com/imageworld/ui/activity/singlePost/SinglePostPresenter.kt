package com.imageworld.ui.activity.singlePost

import com.imageworld.model.Post

class SinglePostPresenter(private val view: SinglePostContract.View)
    : SinglePostContract.Presenter {

    override fun setPostData(post: Post) {
        view.updateUi(post)
    }
}