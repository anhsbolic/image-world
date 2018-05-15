package com.imageworld.ui.fragment.home

import android.os.Handler
import com.imageworld.model.Post
import com.parse.ParseFile
import com.parse.ParseObject
import com.parse.ParseQuery
import com.parse.ParseUser

class HomePresenter(private val view: HomeContract.View) : HomeContract.Presenter {

    override fun loadPostListData() {
        view.showProgress()

        val postList : MutableList<Post> = ArrayList()

        val user = ParseUser.getCurrentUser()
        val query: ParseQuery<ParseObject> = ParseQuery.getQuery("UserPost")
        query.whereNotEqualTo("user", user)
        query.findInBackground { userPosts, e ->
            if (e == null) {
                if (userPosts.isNotEmpty()) {
                    val lastIndex = userPosts.lastIndex
                    for (userPost in userPosts) {
                        val objectId = userPost.objectId
                        val imgFile : ParseFile = userPost.getParseFile("imagePost")
                        val imgPost = imgFile.url
                        val caption = userPost.getString("caption")
                        val totalComments = userPost.getInt("totalComments")

                        val userhavePost: ParseUser = userPost.getParseUser("user")
                        val userhavePostId = userhavePost.objectId

                        val queryUser: ParseQuery<ParseObject> = ParseQuery.getQuery("_User")
                        queryUser.whereEqualTo("objectId", userhavePostId)
                        queryUser.findInBackground { users, er ->
                            if (er == null) {
                                for (theUser in users) {
                                    val username = theUser.getString("username")
                                    val imgProfile : ParseFile? = theUser.getParseFile("image_profile")
                                    var urlImgProfile: String? = null
                                    if (imgProfile != null) {
                                        urlImgProfile = imgProfile.url
                                    }
                                    val post = Post(objectId, urlImgProfile, username, imgPost, caption, totalComments)
                                    postList.add(post)

                                    if (postList.lastIndex == lastIndex) {
                                        Handler().postDelayed({
                                            view.hideProgress()
                                            view.hidePlaceholder()
                                            view.showPostList(postList)
                                        },1800)
                                    }
                                }
                            }
                        }
                    }
                } else {
                    Handler().postDelayed({
                        view.hideProgress()
                        view.showPlaceholder()
                    },1800)
                }
            }
        }
    }
}