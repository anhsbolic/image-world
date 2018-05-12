package com.imageworld.ui.activity.splashscreen

import android.content.Context
import android.support.v7.app.AppCompatActivity

class SplashScreenPresenter (val view : SplashScreenContract.View)
    : SplashScreenContract.Presenter {

    override fun checkUser(context: Context) {
        val isLogin = isLogin(context)

        if (isLogin) {
            view.goToDashboard()
        } else {
            view.goToLogin()
        }
    }

    private fun isLogin(context:Context): Boolean {
        var isLogin = false

        val pref = context.getSharedPreferences("LoginPref", AppCompatActivity.MODE_PRIVATE)
        if (pref.contains("LoginToken")) {
            isLogin = true
        }

        return isLogin
    }

}