package com.example.singer.detail.ui.Adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.singer.detail.R
import com.example.singer.detail.ui.bean.Song

class SongAdapter() : ListAdapter<Song, SongAdapter.ViewHolder>(ItemDiffCallback()) {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val  name:TextView=itemView.findViewById(R.id.music_name)
        val  singer:TextView=itemView.findViewById(R.id.music_singer)

    }

    @SuppressLint("SuspiciousIndentation")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_song, parent, false)
      val viewHolder= ViewHolder(view)
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.name.text = item.name
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