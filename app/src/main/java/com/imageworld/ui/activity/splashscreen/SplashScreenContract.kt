package com.imageworld.ui.activity.splashscreen

interface SplashScreenContract {

    interface View {
        fun goToLogin()

        fun goToDashboard()
    }

    interface Presenter {
        fun checkUser()
    }
}