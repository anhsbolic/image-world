package com.imageworld.ui.activity.splashscreen

class SplashScreenPresenter (val view : SplashScreenContract.View)
    : SplashScreenContract.Presenter {

    override fun checkUser() {
        val isLogin = false

        if (isLogin) {
            view.goToDashboard()
        } else {
            view.goToLogin()
        }
    }

}