package com.imageworld.ui.activity.comment

import android.os.Handler
import com.imageworld.model.PostComment
import com.imageworld.model.UserProfile
import com.parse.ParseFile
import com.parse.ParseObject
import com.parse.ParseUser

class CommentPresenter(private val view: CommentContract.View) : CommentContract.Presenter {
    override fun getUserData() {
        val user = ParseUser.getCurrentUser()
        val id = user.objectId
        val username = user.username
        val firstname = user.getString("first_name")
        val lastname = user.getString("last_name")
        val bio = user.getString("bio")
        var urlImgProfile: String? = null

        val file : ParseFile? = user.getParseFile("image_profile")
        if (file != null) {
            urlImgProfile = file.url
        }

        val userProfile = UserProfile(
                id,
                urlImgProfile,
                firstname,
                lastname,
                username,
                bio)
        view.setUserData(userProfile)
    }

    override fun getPostCommentList() {
    }

    override fun postComment(postComment: PostComment) {
        view.showProgress()

        val postId = postComment.postId
        val urlImgProfile = postComment.urlImgProfile
        val username = postComment.username
        val content = postComment.content

        //Create Parse Object
        val comment = ParseObject("PostComment")
        comment.put("postId", postId)
        comment.put("urlImgProfile", urlImgProfile)
        comment.put("username", username)
        comment.put("content", content)

        //Save Post
        comment.saveInBackground { e ->
            if (e == null) {
                val id = comment.objectId
                postComment.id = id
                view.hideProgress()
                view.showResultPostComment(postComment)
            } else {
                Handler().postDelayed({
                    view.hideProgress()
                    view.showAddPostError(e.message)
                },800)
            }
        }
    }
}