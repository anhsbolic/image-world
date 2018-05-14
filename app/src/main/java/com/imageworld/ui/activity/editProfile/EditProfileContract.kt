package com.imageworld.ui.activity.editProfile

import android.graphics.Bitmap
import com.imageworld.model.UserProfile

interface EditProfileContract {

    interface View {
        fun setInitProfile(userProfile: UserProfile)

        fun showGallery()

        fun setImageProfile(bitmap: Bitmap?)

        fun showErrorInput(isFirstNameValid: Boolean,
                           isUsernameValid: Boolean)

        fun showErrorSaveProfile(e: String)

        fun showProgress()

        fun hideProgress()

        fun showEditProfileResult()

        fun goToDashboard()
    }

    interface Presenter {
        fun changePhoto()

        fun saveProfile(mode: Int, imgProfile: Bitmap?, firstName: String, lastName: String,
                        username: String, bio: String)
    }
}