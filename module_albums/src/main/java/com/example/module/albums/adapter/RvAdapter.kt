package com.example.module.albums.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.module.albums.R
import com.example.module.albums.bean.Song

class RvAdapter(): ListAdapter<Song, RvAdapter.ViewHolder>(ItemDiffCallback()) {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val  name: TextView =itemView.findViewById(R.id.tv_music_name)
        val  songlist: TextView =itemView.findViewById(R.id.singlist)
        val  singer: TextView =itemView.findViewById(R.id.tv_music_singer)

    }

    @SuppressLint("SuspiciousIndentation")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_song, parent, false)
        val viewHolder=ViewHolder(view)
        viewHolder.itemView.setOnClickListener {
            Toast.makeText(parent.context,"不好意思，多模块还没做", Toast.LENGTH_SHORT).show()
        }

        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.name.text = item.name
        holder.songlist.text=item.al.name
        holder.singer.text=item.ar[0].name
        holder.itemView.setOnClickListener {

        }
    }
    class ItemDiffCallback : DiffUtil.ItemCallback<Song>() {
        override fun areItemsTheSame(oldItem: Song, newItem: Song): Boolean {
            // 使用唯一的标识符来比较是否是相同的项
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Song, newItem: Song): Boolean {
            // 比较项的内容
            return oldItem == newItem
        }
    }

}