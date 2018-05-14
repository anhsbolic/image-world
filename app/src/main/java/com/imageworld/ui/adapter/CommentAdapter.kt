package com.imageworld.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.bumptech.glide.Glide
import com.imageworld.R
import com.imageworld.model.PostComment
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.component_comment.view.*

class CommentAdapter(private val commentList: List<PostComment>)
    : RecyclerView.Adapter<CommentAdapter.ViewHolder>(){

    private lateinit var mContext: Context

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        internal var imgProfile: CircleImageView = itemView.commentAdapterImgProfile
        internal var txtUsername: TextView = itemView.commentAdapterTxtUsername
        internal var txtContent: TextView = itemView.commentAdapterTxtContent
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        mContext = parent.context
        val itemView = LayoutInflater.from(mContext).inflate(R.layout.component_comment, parent,
                false)

        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val urlImgProfile = commentList[position].urlImgProfile
        if (urlImgProfile != null) {
            Glide.with(mContext).load(urlImgProfile).into(holder.imgProfile)
        } else {
            Glide.with(mContext).load(R.drawable.ic_img_profile_default).into(holder.imgProfile)
        }

        val username = commentList[position].username
        holder.txtUsername.text = username

        val content = commentList[position].content
        holder.txtContent.text = content
    }

    override fun getItemCount(): Int {
        return commentList.size
    }
}