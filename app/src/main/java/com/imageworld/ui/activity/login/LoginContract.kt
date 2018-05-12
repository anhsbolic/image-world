package com.imageworld.ui.activity.login

import android.content.Context

interface LoginContract {

    interface View {
        fun showProgress()

        fun hideProgress()

        fun goToDashboard()

        fun goToRegister()
    }

    interface Presenter {
        fun signIn(context: Context, token: String)

        fun googleSignIn()

        fun register()
    }
}