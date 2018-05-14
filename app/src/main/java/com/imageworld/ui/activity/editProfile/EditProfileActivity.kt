package com.imageworld.ui.activity.editProfile

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.support.v7.app.ActionBar
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.imageworld.R
import com.imageworld.model.UserProfile
import com.imageworld.ui.activity.dashboard.DashboardActivity
import com.parse.ParseFile
import com.parse.ParseObject
import com.parse.ParseUser
import kotlinx.android.synthetic.main.activity_edit_profile.*
import java.io.ByteArrayOutputStream
import java.io.IOException

class EditProfileActivity : AppCompatActivity(), EditProfileContract.View {

    private lateinit var presenter: EditProfilePresenter
    private lateinit var actionBar: ActionBar
    private lateinit var userProfile: UserProfile
    private var mode: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        setSupportActionBar(editProfileToolbar)
        editProfileToolbar.setNavigationOnClickListener {
            onBackPressed()
        }
        actionBar = supportActionBar!!
        actionBar.title = "Edit Profile"
//        actionBar.setDisplayHomeAsUpEnabled(true)
//        actionBar.setDisplayShowHomeEnabled(true)

        //Init Presenter
        presenter = EditProfilePresenter(this@EditProfileActivity)

        // Get mode data
        if (intent.hasExtra(INTENT_MODE)) {
            mode = intent.getIntExtra(INTENT_MODE,1)
        }

        //Get user data
        if (intent.hasExtra(INTENT_USER)) {
            userProfile = intent.getParcelableExtra(INTENT_USER)
            setInitProfile(userProfile)
        }

        //Change Photo
        editProfileTxtChangePhoto.setOnClickListener { presenter.changePhoto() }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_edit_profile, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when(item!!.itemId){
            android.R.id.home ->{
                onBackPressed()
                true
            }

            R.id.editProfileMenuSave-> {
                presenter.saveProfile(mode)
                true
            }

            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    override fun setInitProfile(userProfile: UserProfile) {
        val imgProfile = userProfile.imageProfile
        val firstName = userProfile.firstName
        val lastName = userProfile.lastName
        val username = userProfile.username
        val bio = userProfile.bio

        if (imgProfile != null) {
            Glide.with(this@EditProfileActivity)
                    .load(imgProfile)
                    .into(editProfileImg)
        } else {
            Glide.with(this@EditProfileActivity)
                    .load(R.drawable.ic_img_profile_default)
                    .into(editProfileImg)
        }

        editProfileEtFirstName.setText(firstName)
        editProfileEtLastName.setText(lastName)
        editProfileEtUsername.setText(username)
        editProfileEtBio.setText(bio)
    }

    override fun showGallery() {
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
                setImageProfile(bitmap)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    override fun setImageProfile(bitmap: Bitmap) {
        Glide.with(this@EditProfileActivity)
                .load(bitmap)
                .into(editProfileImg)
    }

    override fun showProgress() {
        editProfileProgressBarLayout.visibility = View.VISIBLE
        editProfileTxtChangePhoto.isEnabled = false
        editProfileEtFirstName.isEnabled = false
        editProfileEtLastName.isEnabled = false
        editProfileEtUsername.isEnabled = false
        editProfileEtBio.isEnabled = false
    }

    override fun hideProgress() {
        editProfileTxtChangePhoto.isEnabled = true
        editProfileEtFirstName.isEnabled = true
        editProfileEtLastName.isEnabled = true
        editProfileEtUsername.isEnabled = true
        editProfileEtBio.isEnabled = true
        editProfileProgressBarLayout.visibility = View.GONE
    }


    override fun showEditProfileResult() {
    }

    override fun goToDashboard() {
        val intentDashboard = Intent(this@EditProfileActivity, DashboardActivity::class.java)
        startActivity(intentDashboard)
        finishAffinity()
    }

    companion object {
        const val INTENT_USER = "User"
        const val INTENT_MODE = "Mode"

        const val MODE_FIRST_EDIT = 0
        const val MODE_EDIT = 1
    }
}
