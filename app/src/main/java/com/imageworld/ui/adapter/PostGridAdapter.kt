package com.imageworld.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.imageworld.R
import com.imageworld.model.Post
import kotlinx.android.synthetic.main.component_post_grid.view.*

class PostGridAdapter(private val postList: List<Post>,
                      private val onPhotoClickListener: PostGridAdapter.OnPhotoClickListener)
    : RecyclerView.Adapter<PostGridAdapter.ViewHolder>(){

    private lateinit var mContext: Context

    interface OnPhotoClickListener {
        fun onPhotoClick(post: Post)
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        internal var imgPost: ImageView = itemView.postGridImg

        fun setOnClickListener(onClickListener: View.OnClickListener){
            itemView.setOnClickListener(onClickListener)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        mContext = parent.context
        val itemView = LayoutInflater.from(mContext).inflate(R.layout.component_post_grid, parent,
                false)

        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(mContext).load(postList[position].imagePost).into(holder.imgPost)

        holder.setOnClickListener(View.OnClickListener {
            onPhotoClickListener.onPhotoClick(postList[holder.adapterPosition])
        })
    }

    override fun getItemCount(): Int {
        return postList.size
    }
}