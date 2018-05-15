package com.imageworld.ui.activity.comment

import com.imageworld.model.PostComment
import com.imageworld.model.UserProfile

interface CommentContract {
    interface View {
        fun initRecyclerView()

        fun showProgress()

        fun hideProgress()

        fun setUserData(userProfile: UserProfile)

        fun showCommentList(postCommentList: List<PostComment>)

        fun showAddPostError(e: String?)

        fun showResultPostComment(postComment: PostComment)
    }

    interface Presenter{
        fun getUserData()

        fun getPostCommentList(postId: String)

        fun postComment(postComment: PostComment, totalComments: Int)
    }
}