package com.imageworld.ui.activity.editProfile

import android.os.Handler

class EditProfilePresenter(private val view : EditProfileContract.View) : EditProfileContract.Presenter {

    override fun changePhoto() {
        view.showGallery()
    }

    override fun saveProfile(mode: Int) {
        view.showProgress()


        Handler().postDelayed({
            view.hideProgress()
            when(mode){
                0 -> {view.showEditProfileResult()}
                1 -> {view.goToDashboard()}
            }
        },1800)
    }

}