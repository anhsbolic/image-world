package com.imageworld.ui.activity.login

import android.os.Handler

class LoginPresenter(val view : LoginContract.View) : LoginContract.Presenter {
    override fun signIn() {
        view.showProgress()

        Handler().postDelayed({
            view.hideProgress()
            view.goToDashboard()
        },1800)
    }

    override fun googleSignIn() {
    }

    override fun register() {
        view.goToRegister()
    }
}