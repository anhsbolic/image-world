package com.imageworld.ui.activity.login

import android.os.Handler
import com.parse.ParseUser

class LoginPresenter(val view : LoginContract.View) : LoginContract.Presenter {
    override fun signIn(username: String, password: String) {
        val isUsernameValid = isUsernameValid(username)
        val isPasswordValid = isPasswordValid(password)

        if (isUsernameValid && isPasswordValid) {
            view.showErrorInput(true,true)
            view.showProgress()

            ParseUser.logInInBackground(username, password) { user, e ->
                if (user != null) {
                    Handler().postDelayed({
                        Handler().postDelayed({
                            view.hideProgress()
                            view.goToDashboard()
                        },30)
                    },1800)
                } else {
                    view.hideProgress()
                    view.showErrorLogin(e.message)
                }
            }
        } else {
            view.showErrorInput(isUsernameValid, isPasswordValid)
        }
    }

    override fun googleSignIn() {
    }

    override fun register() {
        view.goToRegister()
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