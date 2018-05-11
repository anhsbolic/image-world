package com.imageworld.ui.activity.login

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.imageworld.R
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), LoginContract.View {

    lateinit var presenter: LoginPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Init Presenter
        presenter = LoginPresenter(this@LoginActivity)

        // Sign In
        loginBtnSignIn.setOnClickListener { presenter.signIn() }
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
    }

    override fun goToRegister() {
    }

}
