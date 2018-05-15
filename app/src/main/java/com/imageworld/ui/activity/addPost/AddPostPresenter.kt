package com.imageworld.ui.activity.addPost

import android.graphics.Bitmap
import android.os.Handler
import com.parse.*
import java.io.ByteArrayOutputStream


class AddPostPresenter(private val view: AddPostContract.View) : AddPostContract.Presenter {
    override fun getPhoto() {
        view.openGallery()
    }

    override fun savePost(imgPost: Bitmap, caption: String) {
        view.showProgress()

        //get user
        val user : ParseUser = ParseUser.getCurrentUser()

        //Get imgPost
        val stream = ByteArrayOutputStream()
        imgPost.compress(Bitmap.CompressFormat.PNG, 100, stream)
        val byteArray = stream.toByteArray()
        val fileImgPost = ParseFile("image-post.png", byteArray)

        //Create Parse Object
        val post = ParseObject("UserPost")
        post.put("user", user)
        post.put("imagePost", fileImgPost)
        post.put("caption", caption)
        post.put("totalComments", 0)

        //Save Parse Object : Post
        post.saveInBackground { e ->
            if (e == null) {
                Handler().postDelayed({
                    Handler().postDelayed({
                        view.hideProgress()
                    },500)
                    view.goToProfile()
                },800)
            } else {
                Handler().postDelayed({
                    view.hideProgress()
                    view.showSavePostFailed(e.message)
                },1800)
            }
        }
    }
}