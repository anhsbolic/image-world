package com.imageworld.ui.activity.register

import android.os.Handler
import android.util.Patterns
import com.parse.ParseUser

class RegisterPresenter(val view : RegisterContract.View) : RegisterContract.Presenter {

    override fun register(firstName: String, lastName: String, username: String,
                          email: String, password: String, confirmPassword: String) {

        val isFirstNameValid = isFirstNameValid(firstName)
        val isUsernameValid = isUsernameValid(username)
        val isEmailValid = isEmailValid(email)
        val isPasswordValid = isPasswordValid(password)
        val isConfirmPasswordValid = isConfirmPasswordValid(confirmPassword)

        if (isFirstNameValid && isUsernameValid && isEmailValid
                && isPasswordValid && isConfirmPasswordValid) {

            view.showErrorInput(true,true,true,
                    true,true)

            val isPasswordMatch = isPasswordMatch(password, confirmPassword)
            if (isPasswordMatch) {
                view.showErrorPasswordNotMatch(true)
                view.showProgress()

                val user = ParseUser()

                user.username = username
                user.email = email
                user.setPassword(password)
                user.put("first_name", firstName)
                user.put("last_name",lastName)

                user.signUpInBackground { e ->
                    if (e == null) {
                        Handler().postDelayed({
                            view.hideProgress()
                            view.goToDashboard()
                        }, 1800)
                    } else {
                        view.showRegisterError(e.message)
                    }
                }
            } else {
                view.showErrorPasswordNotMatch(false)
            }
        } else {
            view.showErrorInput(isFirstNameValid, isUsernameValid, isEmailValid,
                    isPasswordValid, isConfirmPasswordValid)
        }
    }

    override fun login() {
        view.backToLogin()
    }

    private fun isFirstNameValid(firstName: String): Boolean {
        var valid = true

        if (firstName.isEmpty()) {
            valid = false
        }

        return valid
    }

    private fun isUsernameValid(username: String): Boolean {
        var valid = true

        if (username.isEmpty()) {
            valid = false
        }

        return valid
    }

    private fun isEmailValid(email: String): Boolean {
        var valid = true

        if (email.isEmpty()) {
            valid = false
        } else {
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                valid = false
            }
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

    private fun isConfirmPasswordValid(confirmPassword: String): Boolean {
        var valid = true

        if (confirmPassword.isEmpty()) {
            valid = false
        }

        return valid
    }

    private fun isPasswordMatch(password: String, confirmPassword: String): Boolean {
        var match = true

        if (confirmPassword != password) {
            match = false
        }

        return match
    }
}