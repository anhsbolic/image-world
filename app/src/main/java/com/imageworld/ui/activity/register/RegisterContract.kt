package com.imageworld.ui.activity.register

import com.imageworld.model.UserProfile

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

        fun showRegisterError(e: String?)

        fun backToLogin()

        fun goToEditProfilePage(userProfile: UserProfile)

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