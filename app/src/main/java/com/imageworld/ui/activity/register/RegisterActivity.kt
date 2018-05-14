package com.imageworld.ui.activity.register

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.imageworld.R
import com.imageworld.model.UserProfile
import com.imageworld.ui.activity.editProfile.EditProfileActivity
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity(), RegisterContract.View {

    private lateinit var presenter: RegisterPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        //Init Presenter
        presenter = RegisterPresenter(this@RegisterActivity)

        //Register
        registerBtnRegister.setOnClickListener {
            val firstname = registerEtFirstName.text.toString().trim()
            val lastname = registerEtLastName.text.toString().trim()
            val username = registerEtUsername.text.toString().trim()
            val email = registerEtEmail.text.toString().trim()
            val password = registerEtPassword.text.toString().trim()
            val confirmPassword = registerEtConfirmPassword.text.toString().trim()
            presenter.register(firstname, lastname, username, email, password, confirmPassword)
        }

        //Back To Login
        registerBtnLogin.setOnClickListener { presenter.login() }
    }

    override fun showErrorInput(isFirstNameValid: Boolean, isUsernameValid: Boolean,
                                isEmailValid: Boolean, isPasswordValid: Boolean,
                                isConfirmPasswordValid: Boolean) {
        // Confirm Password
        if (!isConfirmPasswordValid) {
            registerEtConfirmPasswordLayout.isErrorEnabled = true
            registerEtConfirmPasswordLayout.error = "retype password"
        } else {
            registerEtConfirmPasswordLayout.error = null
            registerEtConfirmPasswordLayout.isErrorEnabled = false
        }

        // Password
        if (!isPasswordValid) {
            registerEtPasswordLayout.isErrorEnabled = true
            registerEtPasswordLayout.error = "require password"
        } else {
            registerEtPasswordLayout.error = null
            registerEtPasswordLayout.isErrorEnabled = false
        }

        // Email
        if (!isEmailValid) {
            registerEtEmailLayout.isErrorEnabled = true
            registerEtEmailLayout.error = "require email"
        } else {
            registerEtEmailLayout.error = null
            registerEtEmailLayout.isErrorEnabled = false
        }

        // Username
        if (!isUsernameValid) {
            registerEtUsernameLayout.isErrorEnabled = true
            registerEtUsernameLayout.error = "require username"
        } else {
            registerEtUsernameLayout.error = null
            registerEtUsernameLayout.isErrorEnabled = false
        }

        // FirstName
        if (!isFirstNameValid) {
            registerEtFirstNameLayout.isErrorEnabled = true
            registerEtFirstNameLayout.error = "require firstname"
        } else {
            registerEtFirstNameLayout.error = null
            registerEtFirstNameLayout.isErrorEnabled = false
        }
    }

    override fun showErrorPasswordNotMatch(isPasswordMatch: Boolean) {
        // Confirm Password
        if (!isPasswordMatch) {
            registerEtConfirmPasswordLayout.isErrorEnabled = true
            registerEtConfirmPasswordLayout.error = "Password not match"
        } else {
            registerEtConfirmPasswordLayout.error = null
            registerEtConfirmPasswordLayout.isErrorEnabled = false
        }
    }

    override fun showProgress() {
        registerProgressBarLayout.visibility = View.VISIBLE
        registerEtFirstName.isEnabled = false
        registerEtLastName.isEnabled = false
        registerEtUsername.isEnabled = false
        registerEtEmail.isEnabled = false
        registerEtPassword.isEnabled = false
        registerEtConfirmPassword.isEnabled = false
        registerBtnRegister.isEnabled = false
        registerBtnLogin.isEnabled = false
    }

    override fun hideProgress() {
        registerEtFirstName.isEnabled = true
        registerEtLastName.isEnabled = true
        registerEtUsername.isEnabled = true
        registerEtEmail.isEnabled = true
        registerEtPassword.isEnabled = true
        registerEtConfirmPassword.isEnabled = true
        registerBtnRegister.isEnabled = true
        registerBtnLogin.isEnabled = true
        registerProgressBarLayout.visibility = View.GONE
    }

    override fun showRegisterError(e: String?) {
        var error = "Registration failed, please try again..."

        if (e != null) {
            error = e
        }

        Toast.makeText(this@RegisterActivity, error, Toast.LENGTH_LONG).show()
    }

    override fun backToLogin() {
        onBackPressed()
    }

    override fun goToEditProfilePage(userProfile: UserProfile) {
        val intentEditProfile = Intent(this@RegisterActivity, EditProfileActivity::class.java)
        intentEditProfile.putExtra(EditProfileActivity.INTENT_MODE, EditProfileActivity.MODE_FIRST_EDIT)
        intentEditProfile.putExtra(EditProfileActivity.INTENT_USER, userProfile)
        startActivity(intentEditProfile)
        finishAffinity()
    }

}