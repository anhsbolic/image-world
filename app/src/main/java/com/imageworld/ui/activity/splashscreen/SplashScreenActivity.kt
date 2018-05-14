package com.imageworld.ui.activity.splashscreen

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.imageworld.R
import com.imageworld.ui.activity.dashboard.DashboardActivity
import com.imageworld.ui.activity.login.LoginActivity
import com.parse.ParseAnalytics

class SplashScreenActivity : AppCompatActivity(), SplashScreenContract.View {

    lateinit var presenter : SplashScreenPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        // Init Parse Analytics
        ParseAnalytics.trackAppOpenedInBackground(intent)

        // Init Presenter
        presenter = SplashScreenPresenter(this@SplashScreenActivity)

        // Check User
        presenter.checkUser()
    }

    override fun goToLogin() {
        val intentLogin = Intent(this@SplashScreenActivity, LoginActivity::class.java)
        startActivity(intentLogin)
        finish()
    }

    override fun goToDashboard() {
        val intentDashboard = Intent(this@SplashScreenActivity, DashboardActivity::class.java)
        startActivity(intentDashboard)
        finish()
    }
}
