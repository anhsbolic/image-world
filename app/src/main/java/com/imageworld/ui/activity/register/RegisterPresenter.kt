package com.imageworld.ui.activity.register

import android.os.Handler

class RegisterPresenter(val view : RegisterContract.View) : RegisterContract.Presenter {

    override fun register() {
        view.showProgress()

        Handler().postDelayed({
            view.hideProgress()
            view.backToLogin()
        }, 2800)
    }

    override fun login() {
        view.backToLogin()
    }
}