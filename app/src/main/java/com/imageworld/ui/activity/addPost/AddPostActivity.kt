package com.imageworld.ui.activity.addPost

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.imageworld.R
import com.imageworld.ui.activity.dashboard.DashboardActivity
import kotlinx.android.synthetic.main.activity_add_post.*
import java.io.IOException

class AddPostActivity : AppCompatActivity(), AddPostContract.View {

    private lateinit var presenter: AddPostPresenter
    private lateinit var actionBar: ActionBar
    private var imgPost: Bitmap? = null
    private var isLoading: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_post)
        setSupportActionBar(addPostToolbar)
        addPostToolbar.setNavigationOnClickListener {
            onBackPressed()
        }
        actionBar = supportActionBar!!
        actionBar.title = "Add Post"
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayShowHomeEnabled(true)

        //Init Presenter
        presenter = AddPostPresenter(this@AddPostActivity)

        //Open Gallery
        presenter.getPhoto()

        //Change Photo
        addPostTxtChangePhoto.setOnClickListener {
            presenter.getPhoto()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add_post, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when(item!!.itemId){
            android.R.id.home ->{
                onBackPressed()
                true
            }

            R.id.addPostMenuSave-> {
                if (!isLoading) {
                    if (imgPost != null) {
                        val caption = addPostEtCaption.text.toString().trim()
                        presenter.savePost(imgPost!!, caption)
                    } else {
                        Toast.makeText(this@AddPostActivity,
                                "You don't have an Image to Post",
                                Toast.LENGTH_LONG).show()
                    }
                }
                true
            }

            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    override fun onBackPressed() {
        if (!isLoading) {
            super.onBackPressed()
        }
    }

    override fun openGallery() {
        val intentPhoto = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intentPhoto, OPEN_GALLERY_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when(requestCode){
            OPEN_GALLERY_CODE -> {
                when(resultCode) {
                    Activity.RESULT_OK -> {
                        if (data != null) {
                            val selectedImage = data.data

                            try {
                                imgPost = MediaStore.Images.Media.getBitmap(this.contentResolver, selectedImage)
                                setImage(imgPost)
                            } catch (e: IOException) {
                                e.printStackTrace()
                            }
                        }
                    }
                }
            }
            else->{
                super.onActivityResult(requestCode, resultCode, data)
            }
        }
    }

    override fun setImage(bmpImgPost: Bitmap?) {
        if (bmpImgPost != null) {
            Glide.with(this@AddPostActivity)
                    .load(bmpImgPost)
                    .into(addPostImg)
        } else {
            Glide.with(this@AddPostActivity)
                    .load(R.drawable.ic_image_placeholder)
                    .into(addPostImg)
        }
    }

    override fun showProgress() {
        isLoading = true
        addPostProgressBarLayout.visibility = View.VISIBLE
        addPostTxtChangePhoto.isEnabled = false
        addPostEtCaption.isEnabled = false
    }

    override fun hideProgress() {
        isLoading = false
        addPostTxtChangePhoto.isEnabled = true
        addPostEtCaption.isEnabled = true
        addPostProgressBarLayout.visibility = View.GONE
    }

    override fun showSavePostFailed(e: String?) {
        var msg = "Add post filed, please try again"
        if (e != null) {
            msg = e
        }
        Toast.makeText(this@AddPostActivity, msg, Toast.LENGTH_LONG).show()
    }

    override fun goToProfile() {
        val intentNavToProfile = Intent(this@AddPostActivity, DashboardActivity::class.java)
        intentNavToProfile.putExtra(DashboardActivity.NAV_TO_PROFILE, true)
        startActivity(intentNavToProfile)
        finishAffinity()
    }

    companion object {
        const val OPEN_GALLERY_CODE : Int = 40
    }

}

