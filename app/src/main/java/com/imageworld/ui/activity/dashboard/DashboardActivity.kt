package com.imageworld.ui.activity.dashboard

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.ActionBar
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import com.imageworld.R
import com.imageworld.ui.fragment.home.HomeFragment
import com.imageworld.ui.fragment.profile.ProfileFragment
import com.parse.ParseFile
import com.parse.ParseObject
import com.parse.ParseUser
import kotlinx.android.synthetic.main.activity_dashboard.*
import java.io.ByteArrayOutputStream
import java.io.IOException


class DashboardActivity : AppCompatActivity() {

    private lateinit var actionBar: ActionBar
    private lateinit var currentFragment : Fragment
    private lateinit var lastFragment : Fragment

    private var isFirstVisit : Boolean = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        setSupportActionBar(dashboardToolbar)
        dashboardToolbar.setNavigationOnClickListener {
            onBackPressed()
        }
        actionBar = supportActionBar!!
        actionBar.title = null

        //Fragment backStack management
        supportFragmentManager.addOnBackStackChangedListener {
            lastFragment = if(supportFragmentManager.backStackEntryCount > 0){
                val fragments = supportFragmentManager.fragments
                fragments[fragments.size-1]
            }else{
                val fragments = supportFragmentManager.fragments
                fragments[0]
            }
        }

        displaySelectedScreen(R.id.nav_home)

        dashboardBottomNav.setOnNavigationItemSelectedListener {item: MenuItem ->
            displaySelectedScreen(item.itemId)
            return@setOnNavigationItemSelectedListener true
        }
    }

    private fun displaySelectedScreen(itemId: Int) {

        when(itemId){
            R.id.nav_home->{
                currentFragment = HomeFragment()
            }
            R.id.nav_camera->{
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1)
                    } else {
                        getPhoto()
                    }
                } else {
                    getPhoto()
                }
            }
            R.id.nav_profile->{
                currentFragment = ProfileFragment()
            }
            else -> {
                currentFragment = HomeFragment()
            }
        }

        if(isFirstVisit){
            isFirstVisit = false
            lastFragment = currentFragment
            supportFragmentManager.beginTransaction()
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .replace(R.id.dashboardFrameContainer, currentFragment)
                    .commit()
        }else{
            if(currentFragment.javaClass.simpleName != lastFragment.javaClass.simpleName){
                supportFragmentManager.beginTransaction()
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .replace(R.id.dashboardFrameContainer, currentFragment,
                                currentFragment.javaClass.simpleName)
                        .addToBackStack(lastFragment.javaClass.simpleName)
                        .commit()
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

    // Photo
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 1) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getPhoto()
            }
        }
    }

    private fun getPhoto() {
        val intentPhoto = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intentPhoto, 1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1 && resultCode == Activity.RESULT_OK && data != null) {

            val selectedImage = data.data

            try {
                val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, selectedImage)
                Log.i("Photo", "Received")
                val stream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
                val byteArray = stream.toByteArray()
                val file = ParseFile("image.png", byteArray)
                val imageObject= ParseObject("Image")
                imageObject.put("image", file)
                imageObject.put("username", ParseUser.getCurrentUser().username)
                imageObject.saveInBackground { e ->
                    if (e == null) {
                        Toast.makeText(this@DashboardActivity, "Image Shared!", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this@DashboardActivity, "Image could not be shared - please try again later.", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}
