package com.imageworld.ui.activity.editProfile

import android.graphics.Bitmap
import com.imageworld.model.UserProfile

interface EditProfileContract {

    interface View {
        fun setInitProfile(userProfile: UserProfile)

        fun showGallery()

        fun setImageProfile(bitmap: Bitmap)

        fun showProgress()

        fun hideProgress()

        fun showEditProfileResult()

        fun goToDashboard()
    }

    interface Presenter {
        fun changePhoto()

        fun saveProfile(mode: Int)
    }
}