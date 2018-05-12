package com.imageworld.ui.activity.login

import android.content.Context
import android.os.Handler

class LoginPresenter(val view : LoginContract.View) : LoginContract.Presenter {
    override fun signIn(username: String, password: String, context: Context, token: String) {
        val isUsernameValid = isUsernameValid(username)
        val isPasswordValid = isPasswordValid(password)

        if (isUsernameValid && isPasswordValid) {
            view.showErrorInput(true,true)
            view.showProgress()

            saveLoginToken(context, token)

            Handler().postDelayed({
                view.hideProgress()
                Handler().postDelayed({
                    view.goToDashboard()
                },30)
            },1800)
        } else {
            view.showErrorInput(isUsernameValid, isPasswordValid)
        }
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

    private fun isUsernameValid(username: String): Boolean {
        var valid = true

        if (username.isEmpty()) {
            valid = false
        }

        return valid
    }

    private fun isPasswordValid(password: String): Boolean {
        var valid = true

        if (password.isEmpty()) {
            valid = false
        }

        return valid
    }
}