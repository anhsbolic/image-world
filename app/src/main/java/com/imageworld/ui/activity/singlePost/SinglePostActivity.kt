package com.imageworld.ui.activity.singlePost

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.ActionBar
import android.view.View
import com.bumptech.glide.Glide
import com.imageworld.R
import com.imageworld.model.Post
import com.imageworld.ui.activity.comment.CommentActivity
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

        // PostComment
        postComment.setOnClickListener {
            val postId = post.id!!
            val totalComments = post.totalComments
            val intentComment = Intent(this@SinglePostActivity, CommentActivity::class.java)
            intentComment.putExtra(CommentActivity.INTENT_POST_ID, postId)
            intentComment.putExtra(CommentActivity.INTENT_TOTAL_COMMENTS, totalComments)
            startActivity(intentComment)
        }

        postTxtSeeComments.setOnClickListener {
            val postId = post.id!!
            val totalComments = post.totalComments
            val intentComment = Intent(this@SinglePostActivity, CommentActivity::class.java)
            intentComment.putExtra(CommentActivity.INTENT_POST_ID, postId)
            intentComment.putExtra(CommentActivity.INTENT_TOTAL_COMMENTS, totalComments)
            startActivity(intentComment)
        }
    }

    override fun updateUi(post: Post) {
        val imgProfile = post.imageProfile
        if (imgProfile != null) {
            Glide.with(this@SinglePostActivity).load(imgProfile).into(postImgProfile)
        } else {
            Glide.with(this@SinglePostActivity).load(R.drawable.ic_img_profile_default).into(postImgProfile)
        }
        postTxtUsername.text = post.username
        Glide.with(this@SinglePostActivity).load(post.imagePost).into(postImg)
        postTxtPostUsername.text = post.username
        postTxtPost.text = post.caption
        val totalComments = post.totalComments
        if (totalComments != null && totalComments > 0) {
            postTxtSeeComments.visibility = View.VISIBLE
            val seeComments = "see $totalComments comments"
            postTxtSeeComments.text = seeComments
        } else {
            postTxtSeeComments.visibility = View.INVISIBLE
        }
    }

    companion object {
        const val INTENT_POST = "IntentPost"
    }
}
