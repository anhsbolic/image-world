package com.imageworld.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.imageworld.R
import com.imageworld.model.Post
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.component_post.view.*

class PostListAdapter(private val postList : List<Post>,
                      private val onCommentClickListener: PostListAdapter.OnCommentClickListener)
    : RecyclerView.Adapter<PostListAdapter.ViewHolder>(){

    private lateinit var mContext: Context

    interface OnCommentClickListener{
        fun onCommentClick(postId: String)
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        internal var imgProfile: CircleImageView = itemView.postImgProfile
        internal var txtUsername: TextView = itemView.postTxtUsername
        internal var imgPost: ImageView = itemView.postImg
        internal var txtComment: TextView = itemView.postComment
        internal var txtPostUsername: TextView = itemView.postTxtPostUsername
        internal var txtPost: TextView = itemView.postTxtPost
        internal var txtSeeComments: TextView = itemView.postTxtSeeComments
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        mContext = parent.context
        val itemView = LayoutInflater.from(mContext).inflate(R.layout.component_post, parent,
                false)

        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val imgProfile = postList[position].imageProfile
        if (imgProfile != null) {
            Glide.with(mContext).load(imgProfile).into(holder.imgProfile)
        } else {
            Glide.with(mContext).load(R.drawable.ic_img_profile_default).into(holder.imgProfile)
        }
        holder.txtUsername.text = postList[position].username
        Glide.with(mContext).load(postList[position].imagePost).into(holder.imgPost)
        holder.txtPostUsername.text = postList[position].username
        holder.txtPost.text = postList[position].caption
        val totalComments = postList[position].totalComments
        if (totalComments != null && totalComments > 0) {
            holder.txtSeeComments.visibility = View.VISIBLE
            val seeComments = "see $totalComments comments"
            holder.txtSeeComments.text = seeComments
        } else {
            holder.txtSeeComments.visibility = View.INVISIBLE
        }

        holder.txtSeeComments.setOnClickListener {
            val postId = postList[holder.adapterPosition].id!!
            onCommentClickListener.onCommentClick(postId)
        }

        holder.txtComment.setOnClickListener {
            val postId = postList[holder.adapterPosition].id!!
            onCommentClickListener.onCommentClick(postId)
        }
    }

    override fun getItemCount(): Int {
        return postList.size
    }
}