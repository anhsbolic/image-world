package com.imageworld.ui.activity.comment

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.imageworld.R
import com.imageworld.model.PostComment
import com.imageworld.model.UserProfile
import kotlinx.android.synthetic.main.activity_comment.*

class CommentActivity : AppCompatActivity(), CommentContract.View {

    private lateinit var presenter: CommentPresenter
    private lateinit var postId: String
    private lateinit var userProfile: UserProfile

    private var commentList: MutableList<PostComment> = ArrayList()
    private lateinit var adapterRvComment: RecyclerView.Adapter<*>
    private lateinit var layoutManagerRvComment: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comment)

        presenter = CommentPresenter(this@CommentActivity)

        initRecyclerView()

        if (intent.hasExtra(INTENT_POST_ID)) {
            postId = intent.getStringExtra(INTENT_POST_ID)
        }

        // get user data
        presenter.getUserData()

        // Post Comment
        commentTxtPost.setOnClickListener {
            val postContent = commentEtPost.text.toString().trim()
            if (postContent.isNotEmpty()) {
                val urlImgProfile = userProfile.imageProfile
                val username = userProfile.username
                val postComment = PostComment(null, postId, urlImgProfile, username, postContent)
                presenter.postComment(postComment)
            } else {
                Toast.makeText(this@CommentActivity, "Leave some comment..",
                        Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun initRecyclerView() {
//        adapterRvComment =
//        layoutManagerRvComment = LinearLayoutManager(this@CommentActivity)
//        commentRv.adapter = adapterRvComment
//        commentRv.layoutManager = layoutManagerRvComment
//        commentRv.setHasFixedSize(false)
    }

    override fun showProgress() {
        if (commentProgressBar != null) {
            commentProgressBar.visibility = View.VISIBLE
        }
    }

    override fun hideProgress() {
        if (commentProgressBar != null) {
            commentProgressBar.visibility = View.GONE
        }
    }

    override fun setUserData(userProfile: UserProfile) {
        this.userProfile = userProfile
        val urlImgProfile = this.userProfile.imageProfile
        if (urlImgProfile != null) {
            Glide.with(this@CommentActivity).load(urlImgProfile).into(commentImgProfile)
        } else {
            Glide.with(this@CommentActivity).load(R.drawable.ic_img_profile_default).into(commentImgProfile)
        }
    }

    override fun showCommentList(postCommentList: List<PostComment>) {
        if (commentRv != null) {
            if (!commentRv.isShown) {
                commentRv.visibility = View.VISIBLE
            }
        }
    }

    override fun showAddPostError(e: String?) {
        var msg = "Add post failed, please try again"
        if (e != null) {
            msg = e
        }
        Toast.makeText(this@CommentActivity, msg, Toast.LENGTH_LONG).show()
    }

    override fun showResultPostComment(postComment: PostComment) {
        Log.d("TES", postComment.toString())
    }

    companion object {
        const val INTENT_POST_ID = "PostId"
    }
}
