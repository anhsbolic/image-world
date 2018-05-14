package com.imageworld.ui.activity.singlePost

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.ActionBar
import com.bumptech.glide.Glide
import com.imageworld.R
import com.imageworld.model.Post
import kotlinx.android.synthetic.main.activity_single_post.*
import kotlinx.android.synthetic.main.component_post.*

class SinglePostActivity : AppCompatActivity(), SinglePostContract.View {

    private lateinit var presenter: SinglePostPresenter
    private lateinit var actionBar: ActionBar
    private lateinit var post: Post

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_post)
        setSupportActionBar(singlePostToolbar)
        singlePostToolbar.setNavigationOnClickListener {
            onBackPressed()
        }
        actionBar = supportActionBar!!
        actionBar.title = "Photo"
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayShowHomeEnabled(true)

        //Init Presenter
        presenter = SinglePostPresenter(this@SinglePostActivity)

        // Get Post Data
        if (intent.hasExtra(INTENT_POST)) {
            post = intent.getParcelableExtra(INTENT_POST)

            // Update UI
            presenter.setPostData(post)
        }
    }

    override fun updateUi(post: Post) {
        Glide.with(this@SinglePostActivity).load(post.imageProfile).into(postImgProfile)
        postTxtUsername.text = post.username
        Glide.with(this@SinglePostActivity).load(post.imagePost).into(postImg)
//        postTxtSeenBy.text = post.seenBy
//        postTxtPost.text = post.post
//        val totalComments = post.totalComment
//        val seeComments = "see $totalComments comments"
//        postTxtSeeComments.text = seeComments
    }

    companion object {
        const val INTENT_POST = "IntentPost"
    }
}
