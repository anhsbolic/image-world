package com.imageworld.ui.activity.comment

import android.os.Handler
import com.imageworld.model.PostComment
import com.imageworld.model.UserProfile
import com.parse.*

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

    override fun getPostCommentList(postId: String) {
        view.showProgress()

        val commentList: MutableList<PostComment> = ArrayList()

        val query: ParseQuery<ParseObject> = ParseQuery.getQuery("PostComment")
        query.whereEqualTo("postId", postId)
        query.findInBackground { comments, e ->
            if (e == null) {
                if (comments.isNotEmpty()) {
                    for (comment in comments) {
                        val id = comment.objectId
                        val urlImgProfile = comment.getString("urlImgProfile")
                        val username  = comment.getString("username")
                        val content = comment.getString("content")

                        val postComment = PostComment(id, postId, urlImgProfile, username, content)
                        commentList.add(postComment)
                    }
                    view.hideProgress()
                    view.showCommentList(commentList)
                } else {
                    view.hideProgress()
                    view.showCommentList(commentList)
                }
            } else {
                view.hideProgress()
            }
        }
    }

    override fun postComment(postComment: PostComment, totalComments: Int) {
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

                val query = ParseQuery.getQuery<ParseObject>("UserPost")
                query.getInBackground(postId) { userPost, er ->
                    if (er == null) {
                        if (userPost != null) {
                            val updateTotalComments = totalComments + 1
                            userPost.put("totalComments", updateTotalComments)
                            userPost.saveInBackground{error ->
                                if (error == null) {
                                    val id = comment.objectId
                                    postComment.id = id
                                    view.hideProgress()
                                    view.showResultPostComment(postComment)
                                } else {
                                    Handler().postDelayed({
                                        view.hideProgress()
                                        view.showAddPostError(error.message)
                                    },800)
                                }
                            }
                        } else {
                            Handler().postDelayed({
                                view.hideProgress()
                                view.showAddPostError("Something wrong, please try again")
                            },800)
                        }
                    } else {
                        Handler().postDelayed({
                            view.hideProgress()
                            view.showAddPostError(er.message)
                        },800)
                    }
                }
            } else {
                Handler().postDelayed({
                    view.hideProgress()
                    view.showAddPostError(e.message)
                },800)
            }
        }
    }
}