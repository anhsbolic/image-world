package com.imageworld.ui.activity.login

import android.content.Context

interface LoginContract {

    interface View {
        fun showErrorInput(isUsernameValid: Boolean, isPasswordValid: Boolean)

        fun showProgress()

        fun hideProgress()

        fun goToDashboard()

        fun goToRegister()
    }

    interface Presenter {
        fun signIn(username: String, password: String, context: Context, token: String)

        fun googleSignIn()

        fun register()
    }
}