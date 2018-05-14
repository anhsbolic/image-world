package com.imageworld.ui.fragment.profile

import com.imageworld.R
import com.imageworld.model.Post
import com.imageworld.model.UserProfile
import com.parse.ParseFile
import com.parse.ParseUser

class ProfilePresenter(private val view : ProfileContract.View) : ProfileContract.Presenter {

    private lateinit var username : String
    override fun getProfile() {
        val user = ParseUser.getCurrentUser()
        val id = user.objectId
        username = user.username
        val firstname = user.getString("first_name")
        val lastname = user.getString("last_name")
        val bio = user.getString("bio")

        val file : ParseFile = user.get("image_profile") as ParseFile
        val urlImgProfile = file.url
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
        val postList : MutableList<Post> = ArrayList()

        if (postList.isNotEmpty()) {
            view.hidePlaceholder()
            view.setGridView(postList)
        } else {
            view.showPlaceholder()
        }
    }

    override fun listView() {
        val postList : MutableList<Post> = ArrayList()
        if (postList.isNotEmpty()) {
            view.hidePlaceholder()
            view.setListView(postList)
        } else {
            view.showPlaceholder()
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