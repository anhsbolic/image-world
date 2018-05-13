package com.imageworld.ui.activity.login

interface LoginContract {

    interface View {
        fun showErrorInput(isUsernameValid: Boolean, isPasswordValid: Boolean)

        fun showProgress()

        fun hideProgress()

        fun showErrorLogin(e: String?)

        fun goToDashboard()

        fun goToRegister()
    }

    interface Presenter {
        fun signIn(username: String, password: String)

        fun googleSignIn()

        fun register()
    }
}