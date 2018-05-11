package com.imageworld.ui.activity.login

interface LoginContract {

    interface View {
        fun showProgress()

        fun hideProgress()

        fun goToDashboard()

        fun goToRegister()
    }

    interface Presenter {
        fun signIn()

        fun googleSignIn()

        fun register()
    }
}