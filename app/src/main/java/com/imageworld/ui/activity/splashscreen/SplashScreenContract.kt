package com.imageworld.ui.activity.splashscreen

import android.content.Context

interface SplashScreenContract {

    interface View {
        fun goToLogin()

        fun goToDashboard()
    }

    interface Presenter {
        fun checkUser(context: Context)
    }
}