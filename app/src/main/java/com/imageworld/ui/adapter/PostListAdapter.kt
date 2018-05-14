package com.imageworld.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.imageworld.R
import com.imageworld.model.Post
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.component_post.view.*

class PostListAdapter(private val postList : List<Post>,
                      private val onOptionClickListener: PostListAdapter.OnOptionClickListener)
    : RecyclerView.Adapter<PostListAdapter.ViewHolder>(){

    private lateinit var mContext: Context

    interface OnOptionClickListener{
        fun onOptionClick()
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        internal var imgProfile: CircleImageView = itemView.postImgProfile
        internal var txtUsername: TextView = itemView.postTxtUsername
        internal var btnOptions: ImageButton = itemView.postBtnOption
        internal var imgPost: ImageView = itemView.postImg
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
        Glide.with(mContext).load(postList[position].imageProfile).into(holder.imgProfile)
        holder.txtUsername.text = postList[position].username
        Glide.with(mContext).load(postList[position].imagePost).into(holder.imgPost)
//        holder.txtPost.text = postList[position].post
//        val totalComments = postList[position].totalComment
//        val seeComments = "see $totalComments comments"
//        holder.txtSeeComments.text = seeComments

        holder.btnOptions.setOnClickListener { onOptionClickListener.onOptionClick() }
    }

    override fun getItemCount(): Int {
        return postList.size
    }
}