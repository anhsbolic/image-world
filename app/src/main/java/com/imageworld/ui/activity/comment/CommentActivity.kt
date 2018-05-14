package com.imageworld.ui.activity.comment

import android.os.Bundle
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.imageworld.R
import com.imageworld.model.PostComment
import com.imageworld.model.UserProfile
import com.imageworld.ui.adapter.CommentAdapter
import kotlinx.android.synthetic.main.activity_comment.*

class CommentActivity : AppCompatActivity(), CommentContract.View {

    private lateinit var actionBar: ActionBar
    private lateinit var presenter: CommentPresenter
    private lateinit var postId: String
    private lateinit var userProfile: UserProfile

    private var commentList: MutableList<PostComment> = ArrayList()
    private lateinit var adapterRvComment: RecyclerView.Adapter<*>
    private lateinit var layoutManagerRvComment: RecyclerView.LayoutManager
    private lateinit var dividerItemDecoration: DividerItemDecoration
    private lateinit var itemAnimator: RecyclerView.ItemAnimator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comment)
        setSupportActionBar(commentToolbar)
        commentToolbar.setNavigationOnClickListener {
            onBackPressed()
        }
        actionBar = supportActionBar!!
        actionBar.title = "Comments"
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayShowHomeEnabled(true)

        presenter = CommentPresenter(this@CommentActivity)

        initRecyclerView()

        if (intent.hasExtra(INTENT_POST_ID)) {
            postId = intent.getStringExtra(INTENT_POST_ID)

            // get comment list
            presenter.getPostCommentList(postId)
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
        adapterRvComment = CommentAdapter(commentList)
        layoutManagerRvComment = LinearLayoutManager(this@CommentActivity)
        dividerItemDecoration = DividerItemDecoration(this@CommentActivity, DividerItemDecoration.VERTICAL)
        itemAnimator = DefaultItemAnimator()
        itemAnimator.addDuration = 300
        itemAnimator.removeDuration= 300
        commentRv.adapter = adapterRvComment
        commentRv.layoutManager = layoutManagerRvComment
        commentRv.addItemDecoration(dividerItemDecoration)
        commentRv.itemAnimator = itemAnimator
        commentRv.setHasFixedSize(false)
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

            for (i in 0 until postCommentList.size) {
                commentList.add(postCommentList[i])
                val lastIndex = commentList.lastIndex
                adapterRvComment.notifyItemInserted(lastIndex)
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
        commentList.add(0, postComment)
        adapterRvComment.notifyItemInserted(0)
        commentRv.smoothScrollToPosition(0)
    }

    companion object {
        const val INTENT_POST_ID = "PostId"
    }
}
