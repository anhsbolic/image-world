package com.imageworld.ui.activity.splashscreen

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.imageworld.R

class SplashScreenActivity : AppCompatActivity(), SplashScreenContract.View {

    lateinit var presenter : SplashScreenPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        // Init Presenter
        presenter = SplashScreenPresenter(this@SplashScreenActivity)

        // Check User
        presenter.checkUser()
    }

    override fun goToLogin() {
        Handler().postDelayed({},2800)
    }

    override fun goToDashboard() {
        Handler().postDelayed({},2800)
    }
}
