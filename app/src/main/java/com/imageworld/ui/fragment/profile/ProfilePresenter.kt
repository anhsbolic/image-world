package com.imageworld.ui.fragment.profile

import com.imageworld.R
import com.imageworld.model.Post
import com.imageworld.model.UserProfile
import com.parse.ParseFile
import com.parse.ParseObject
import com.parse.ParseQuery
import com.parse.ParseUser

class ProfilePresenter(private val view : ProfileContract.View) : ProfileContract.Presenter {

    private lateinit var username : String
    private lateinit var urlImgProfile: String

    override fun getProfile() {
        val user = ParseUser.getCurrentUser()
        val id = user.objectId
        username = user.username
        val firstname = user.getString("first_name")
        val lastname = user.getString("last_name")
        val bio = user.getString("bio")

        val file : ParseFile = user.get("image_profile") as ParseFile
        urlImgProfile = file.url
        val userProfile = UserProfile(
                id,
                urlImgProfile,
                firstname,
                lastname,
                username,
                bio)
        view.showProfile(userProfile)
    }

    override fun gridView() {
        view.showProgress()

        val postList : MutableList<Post> = ArrayList()

        val user = ParseUser.getCurrentUser()
        val query: ParseQuery<ParseObject> = ParseQuery.getQuery("UserPost")
        query.whereEqualTo("user", user)
        query.findInBackground { userPosts, e ->
            if (e == null) {
                if (userPosts.isNotEmpty()) {
                    for (userPost in userPosts) {
                        val objectId = userPost.objectId
                        val imgFile : ParseFile = userPost.get("imagePost") as ParseFile
                        val imgPost = imgFile.url
                        val caption = userPost.getString("caption")

                        val post = Post(objectId, urlImgProfile, username, imgPost, caption, null)
                        postList.add(post)
                    }

                    view.hideProgress()
                    view.hidePlaceholder()
                    view.setGridView(postList)
                } else {
                    view.hideProgress()
                    view.showPlaceholder()
                }
            }
        }
    }

    override fun listView() {
        view.showProgress()

        val postList : MutableList<Post> = ArrayList()

        val user = ParseUser.getCurrentUser()
        val query: ParseQuery<ParseObject> = ParseQuery.getQuery("UserPost")
        query.whereEqualTo("user", user)
        query.findInBackground { userPosts, e ->
            if (e == null) {
                if (userPosts.isNotEmpty()) {
                    for (userPost in userPosts) {
                        val objectId = userPost.objectId
                        val imgFile : ParseFile = userPost.get("imagePost") as ParseFile
                        val imgPost = imgFile.url
                        val caption = userPost.getString("caption")

                        val post = Post(objectId, urlImgProfile, username, imgPost, caption, null)
                        postList.add(post)
                    }

                    view.hideProgress()
                    view.hidePlaceholder()
                    view.setListView(postList)
                } else {
                    view.hideProgress()
                    view.showPlaceholder()
                }
            }
        }
    }

    override fun logout() {
        ParseUser.logOutInBackground { e ->
            if (e == null) {
                view.goToLogin()
            } else {
                view.showErrorLogout(e.message)
            }
        }
    }

}