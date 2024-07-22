package com.example.module.broadcast.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.module.broadcast.R
import com.example.module.broadcast.bean.Onecomment

class CommentAdapter() : ListAdapter<Onecomment, CommentAdapter.InnerHolder>(ItemDiffcallback()) {
    class ItemDiffcallback : DiffUtil.ItemCallback<Onecomment>() {
        override fun areItemsTheSame(oldItem: Onecomment, newItem: Onecomment): Boolean {
            return oldItem.user.userId == newItem.user.userId
        }

        override fun areContentsTheSame(oldItem: Onecomment, newItem: Onecomment): Boolean {
            return oldItem == newItem

        }
    }

    class InnerHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ueserImage: ImageView = itemView.findViewById(R.id.commentimage)
        val userName: TextView = itemView.findViewById(R.id.commentName)
        val commentText: TextView = itemView.findViewById(R.id.commentText)
        val putTime: TextView = itemView.findViewById(R.id.putTime)
        val commentNumber: TextView = itemView.findViewById(R.id.commentNumber)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.item_comments,parent,false)
        val viewHolder=InnerHolder(view)
        viewHolder.itemView.setOnClickListener {

        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: InnerHolder, position: Int) {
  val item=getItem(position)
        holder.commentText.text=item.content
        holder.commentNumber.text=item.likedCount.toString()
        holder.userName.text=item.user.nickname
        holder.putTime.text=item.timeStr

        Glide.with(holder.itemView.context).load(item.user.avatarUrl)
            .circleCrop().into(holder.ueserImage)
    }


}