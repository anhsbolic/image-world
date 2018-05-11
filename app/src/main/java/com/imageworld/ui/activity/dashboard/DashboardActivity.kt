package com.imageworld.ui.activity.dashboard

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.ActionBar
import android.support.v7.app.AlertDialog
import android.view.MenuItem
import android.widget.Toast
import com.imageworld.R
import com.imageworld.ui.fragment.home.HomeFragment
import kotlinx.android.synthetic.main.activity_dashboard.*

class DashboardActivity : AppCompatActivity() {

    private lateinit var actionBar: ActionBar
    private var currentFragment: Fragment? = null
    private var isFirstFragment: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        setSupportActionBar(dashboardToolbar)
        dashboardToolbar.setNavigationOnClickListener {
            onBackPressed()
        }
        actionBar = supportActionBar!!
        actionBar.title = null

        displaySelectedScreen(R.id.nav_home)

        dashboardBottomNav.setOnNavigationItemSelectedListener {item: MenuItem ->
            displaySelectedScreen(item.itemId)
            return@setOnNavigationItemSelectedListener true
        }
    }

    private fun displaySelectedScreen(itemId: Int) {
        var fragment: Fragment? = null

        when(itemId){
            R.id.nav_home->{
                fragment = HomeFragment()
            }
            R.id.nav_camera->{
                Toast.makeText(this@DashboardActivity,"CAMERA", Toast.LENGTH_SHORT).show()
            }
            R.id.nav_profile->{
                Toast.makeText(this@DashboardActivity,"PROFILE", Toast.LENGTH_SHORT).show()
            }
            else -> {
                fragment = HomeFragment()
            }
        }

        if(fragment != null){
            if(isFirstFragment){
                isFirstFragment = false
                supportFragmentManager.beginTransaction()
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .replace(R.id.dashboardFrameContainer, fragment)
                        .commit()
                currentFragment = fragment
            }else{
                if(fragment.javaClass.simpleName != currentFragment!!.javaClass.simpleName){
                    supportFragmentManager.beginTransaction()
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                            .replace(R.id.dashboardFrameContainer, fragment)
                            .commit()
                    currentFragment = fragment
                }
            }
        }
    }

    override fun onBackPressed() {
        if(supportFragmentManager.backStackEntryCount > 0){
            super.onBackPressed()
        }else{
            AlertDialog.Builder(this@DashboardActivity)
                    .setMessage(R.string.dashboard_dialog_exit_message)
                    .setPositiveButton(R.string.dashboard_dialog_exit_positive,{ _ , _ ->
                        finish()
                    })
                    .setNegativeButton(R.string.dashboard_dialog_exit_negative, null)
                    .show()
        }
    }
}
