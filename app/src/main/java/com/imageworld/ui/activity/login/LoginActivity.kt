package com.imageworld.ui.activity.login

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.imageworld.R
import com.imageworld.ui.activity.dashboard.DashboardActivity
import com.imageworld.ui.activity.register.RegisterActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), LoginContract.View {

    private lateinit var presenter: LoginPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Init Presenter
        presenter = LoginPresenter(this@LoginActivity)

        // Sign In
        loginBtnSignIn.setOnClickListener {
            val username = loginEtId.text.toString().trim()
            val password = loginEtPassword.text.toString().trim()
            val token = "dsfag35w467i"

            presenter.signIn(username, password,this@LoginActivity, token)
        }

        // Register
        loginBtnRegister.setOnClickListener { presenter.register() }
    }

    override fun showErrorInput(isUsernameValid: Boolean, isPasswordValid: Boolean) {
        //Password
        if (!isPasswordValid) {
            loginEtPasswordLayout.isErrorEnabled = true
            loginEtPasswordLayout.error = "password required"
        } else {
            loginEtPasswordLayout.error = null
            loginEtPasswordLayout.isErrorEnabled = false
        }

        // Username
        if (!isUsernameValid) {
            loginEtIdLayout.isErrorEnabled = true
            loginEtIdLayout.error = "username required"
        } else {
            loginEtIdLayout.error = null
            loginEtIdLayout.isErrorEnabled = false
        }
    }

    override fun showProgress() {
        loginProgressBarLayout.visibility = View.VISIBLE
        loginEtId.isEnabled = false
        loginEtPassword.isEnabled = false
        loginBtnSignIn.isEnabled = false
        loginBtnGoogleSignIn.isEnabled = false
        loginBtnRegister.isEnabled = false
    }

    override fun hideProgress() {
        loginEtId.isEnabled = true
        loginEtPassword.isEnabled = true
        loginBtnSignIn.isEnabled = true
        loginBtnGoogleSignIn.isEnabled = true
        loginBtnRegister.isEnabled = true
        loginProgressBarLayout.visibility = View.GONE
    }

    override fun goToDashboard() {
        val intentDashboard = Intent(this@LoginActivity, DashboardActivity::class.java)
        startActivity(intentDashboard)
        finish()
    }

    override fun goToRegister() {
        val intentRegister = Intent(this@LoginActivity, RegisterActivity::class.java)
        startActivity(intentRegister)
    }

}
