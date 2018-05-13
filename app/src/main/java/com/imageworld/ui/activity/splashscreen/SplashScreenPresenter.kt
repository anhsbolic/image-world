package com.imageworld.ui.activity.splashscreen

import android.os.Handler
import com.parse.ParseUser

class SplashScreenPresenter (val view : SplashScreenContract.View)
    : SplashScreenContract.Presenter {

    override fun checkUser() {
        val currentUser = ParseUser.getCurrentUser()

        if (currentUser != null) {
            Handler().postDelayed({
                view.goToDashboard()
            },800)
        } else {
            Handler().postDelayed({
                view.goToLogin()
            },800)
        }
    }
}