package com.imageworld.ui.activity.login

import android.content.Context
import android.os.Handler

class LoginPresenter(val view : LoginContract.View) : LoginContract.Presenter {
    override fun signIn(context: Context, token: String) {
        view.showProgress()

        saveLoginToken(context, token)

        Handler().postDelayed({
            view.hideProgress()
            Handler().postDelayed({
                view.goToDashboard()
            },30)
        },1800)
    }

    override fun googleSignIn() {
    }

    override fun register() {
        view.goToRegister()
    }

    private fun saveLoginToken(context:Context, token: String) {
        val pref = context.getSharedPreferences("LoginPref", Context.MODE_PRIVATE)
        pref.edit().putString("LoginToken", token).apply()
    }
}