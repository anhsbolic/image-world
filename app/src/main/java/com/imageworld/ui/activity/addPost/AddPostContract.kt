package com.imageworld.ui.activity.addPost

import android.graphics.Bitmap

interface AddPostContract {

    interface View {
        fun openGallery()

        fun setImage(bmpImgPost: Bitmap?)

        fun showProgress()

        fun showSavePostFailed(e: String?)

        fun hideProgress()

        fun goToProfile()
    }

    interface Presenter {
        fun getPhoto()

        fun savePost(imgPost: Bitmap, caption: String)
    }
}