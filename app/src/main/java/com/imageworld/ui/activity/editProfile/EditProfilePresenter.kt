package com.imageworld.ui.activity.editProfile

import android.graphics.Bitmap
import android.os.Handler
import com.imageworld.model.UserProfile
import com.parse.ParseFile
import com.parse.ParseUser
import java.io.ByteArrayOutputStream

class EditProfilePresenter(private val view : EditProfileContract.View) : EditProfileContract.Presenter {

    override fun changePhoto() {
        view.showGallery()
    }

    override fun saveProfile(mode: Int, imgProfile: Bitmap?, firstName: String, lastName: String,
                             username: String, bio: String) {

        val isFirstNameValid = isFirstNameValid(firstName)
        val isUsernameValid = isUsernameValid(username)

        if (isFirstNameValid && isUsernameValid) {
            view.showErrorInput(true,true)
            view.showProgress()

            // info
            val user = ParseUser.getCurrentUser()
            user.username = username
            user.put("first_name", firstName)
            user.put("last_name", lastName)
            user.put("bio", bio)

            // Img Profile
            if (imgProfile != null){
                val stream = ByteArrayOutputStream()
                imgProfile.compress(Bitmap.CompressFormat.PNG, 100, stream)
                val byteArray = stream.toByteArray()
                val file = ParseFile("image-profile.png", byteArray)
                user.put("image_profile", file)
            }

            // Save
            user.saveInBackground { e ->
                if (e == null) {
                    Handler().postDelayed({
                        view.hideProgress()
                        when(mode){
                            0 -> {view.goToDashboard()}
                            1 -> {
                                val file : ParseFile = user.getParseFile("image_profile")
                                val urlImgProfile = file.url
                                val userProfile = UserProfile(user.objectId, urlImgProfile, firstName,
                                        lastName, username, bio)
                                view.showEditProfileResult(userProfile)
                            }
                        }
                    },1800)
                } else {
                    view.hideProgress()
                    view.showErrorSaveProfile(e.message!!)
                }
            }
        } else {
            view.showErrorInput(isFirstNameValid, isUsernameValid)
        }
    }

    private fun isFirstNameValid(firstName: String): Boolean {
        var valid = true

        if (firstName.isEmpty()) {
            valid = false
        }

        return valid
    }

    private fun isUsernameValid(username: String): Boolean {
        var valid = true

        if (username.isEmpty()) {
            valid = false
        }

        return valid
    }

}