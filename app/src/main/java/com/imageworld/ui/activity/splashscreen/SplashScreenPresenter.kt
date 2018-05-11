package com.imageworld.ui.activity.splashscreen

class SplashScreenPresenter (val view : SplashScreenContract.View)
    : SplashScreenContract.Presenter {

    override fun checkUser() {
        val isLogin = true

        if (isLogin) {
            view.goToDashboard()
        } else {
            view.goToLogin()
        }
    }

}