package com.imageworld.ui.fragment.profile

import com.imageworld.R
import com.imageworld.model.Post
import com.imageworld.model.UserProfile
import com.parse.ParseUser

class ProfilePresenter(private val view : ProfileContract.View) : ProfileContract.Presenter {

    override fun getProfile() {
        val userProfile = UserProfile(1,
                R.drawable.img_profil_1,
                "jacksparrow",
                "bio bio bio bio bio bio bio bio bio bio bio bio bio bio bio bio " +
                        "bio bio bio bio bio bio bio bio bio bio bio bio bio bio bio " +
                        "bio bio bio bio bio bio bio bio bio bio bio bio bio bio ")
        view.showProfile(userProfile)
    }

    override fun gridView() {
        val post1 = Post(
                1,
                R.drawable.img_profil_1,
                "jacksparrow",
                R.drawable.img_1,
                "Dude, Jodi, Dean, and others",
                "yeah dude. it is all about PASSION....",
                3952)
        val post2 = Post(
                2,
                R.drawable.img_profil_1,
                "jacksparrow",
                R.drawable.img_2,
                "Dude, Jodi, Dean, and others",
                "yeah dude. it is all about PASSION....",
                3952)
        val post3 = Post(
                3,
                R.drawable.img_profil_1,
                "jacksparrow",
                R.drawable.img_3,
                "Dude, Jodi, Dean, and others",
                "yeah dude. it is all about PASSION....",
                3952)
        val post4 = Post(
                4,
                R.drawable.img_profil_1,
                "jacksparrow",
                R.drawable.img_4,
                "Dude, Jodi, Dean, and others",
                "yeah dude. it is all about PASSION....",
                3952)
        val post5 = Post(
                5,
                R.drawable.img_profil_1,
                "jacksparrow",
                R.drawable.img_5,
                "Dude, Jodi, Dean, and others",
                "yeah dude. it is all about PASSION....",
                3952)
        val post6 = Post(
                6,
                R.drawable.img_profil_1,
                "jacksparrow",
                R.drawable.img_6,
                "Dude, Jodi, Dean, and others",
                "yeah dude. it is all about PASSION....",
                3952)
        val post7 = Post(
                7,
                R.drawable.img_profil_1,
                "jacksparrow",
                R.drawable.img_7,
                "Dude, Jodi, Dean, and others",
                "yeah dude. it is all about PASSION....",
                3952)

        val postList : MutableList<Post> = ArrayList()
        postList.add(post1)
        postList.add(post2)
        postList.add(post3)
        postList.add(post4)
        postList.add(post5)
        postList.add(post6)
        postList.add(post7)

        view.setGridView(postList)
    }

    override fun listView() {
        val post1 = Post(
                1,
                R.drawable.img_profil_1,
                "jacksparrow",
                R.drawable.img_1,
                "Dude, Jodi, Dean, and others",
                "yeah dude. it is all about PASSION....",
                3952)
        val post2 = Post(
                2,
                R.drawable.img_profil_1,
                "jacksparrow",
                R.drawable.img_2,
                "Dude, Jodi, Dean, and others",
                "yeah dude. it is all about PASSION....",
                3952)
        val post3 = Post(
                3,
                R.drawable.img_profil_1,
                "jacksparrow",
                R.drawable.img_3,
                "Dude, Jodi, Dean, and others",
                "yeah dude. it is all about PASSION....",
                3952)
        val post4 = Post(
                4,
                R.drawable.img_profil_1,
                "jacksparrow",
                R.drawable.img_4,
                "Dude, Jodi, Dean, and others",
                "yeah dude. it is all about PASSION....",
                3952)
        val post5 = Post(
                5,
                R.drawable.img_profil_1,
                "jacksparrow",
                R.drawable.img_5,
                "Dude, Jodi, Dean, and others",
                "yeah dude. it is all about PASSION....",
                3952)
        val post6 = Post(
                6,
                R.drawable.img_profil_1,
                "jacksparrow",
                R.drawable.img_6,
                "Dude, Jodi, Dean, and others",
                "yeah dude. it is all about PASSION....",
                3952)
        val post7 = Post(
                7,
                R.drawable.img_profil_1,
                "jacksparrow",
                R.drawable.img_7,
                "Dude, Jodi, Dean, and others",
                "yeah dude. it is all about PASSION....",
                3952)

        val postList : MutableList<Post> = ArrayList()
        postList.add(post1)
        postList.add(post2)
        postList.add(post3)
        postList.add(post4)
        postList.add(post5)
        postList.add(post6)
        postList.add(post7)

        view.setListView(postList)
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