package com.imageworld.ui.activity.register

interface RegisterContract {

    interface View {
        fun showErrorInput(isFirstNameValid: Boolean,
                           isUsernameValid: Boolean,
                           isEmailValid: Boolean,
                           isPasswordValid: Boolean,
                           isConfirmPasswordValid: Boolean)

        fun showErrorPasswordNotMatch(isPasswordMatch: Boolean)

        fun showProgress()

        fun hideProgress()

        fun backToLogin()
    }

    interface Presenter {
        fun register(firstName: String,
                     lastName: String,
                     username: String,
                     email: String,
                     password: String,
                     confirmPassword: String)

        fun login()
    }

}