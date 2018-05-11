package com.imageworld.ui.activity.register

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.imageworld.R
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity(), RegisterContract.View {

    private lateinit var presenter: RegisterPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        //Init Presenter
        presenter = RegisterPresenter(this@RegisterActivity)

        //Register
        registerBtnRegister.setOnClickListener { presenter.register() }

        //Back To Login
        registerBtnLogin.setOnClickListener { presenter.login() }
    }

    override fun showProgress() {
        registerProgressBarLayout.visibility = View.VISIBLE
        registerEtFirstName.isEnabled = false
        registerEtLastName.isEnabled = false
        registerEtUsername.isEnabled = false
        registerEtPassword.isEnabled = false
        registerEtConfirmPassword.isEnabled = false
        registerBtnRegister.isEnabled = false
        registerBtnLogin.isEnabled = false
    }

    override fun hideProgress() {
        registerEtFirstName.isEnabled = true
        registerEtLastName.isEnabled = true
        registerEtUsername.isEnabled = true
        registerEtPassword.isEnabled = true
        registerEtConfirmPassword.isEnabled = true
        registerBtnRegister.isEnabled = true
        registerBtnLogin.isEnabled = true
        registerProgressBarLayout.visibility = View.GONE
    }

    override fun backToLogin() {
        onBackPressed()
    }

}
