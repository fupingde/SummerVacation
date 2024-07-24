package com.example.singer.detail.ui.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.singer.detail.R
import com.example.singer.detail.ui.bean.Song

class RvAdapter():ListAdapter<Song,RvAdapter.InnerHolder>(ItemDiffCallback()) {
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
  class  InnerHolder(itemview:View):RecyclerView.ViewHolder(itemview){
      val name:TextView=itemview.findViewById(R.id.musical_name)
      val singername:TextView=itemview.findViewById(R.id.musical_singers)
  }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerHolder {
        val view=LayoutInflater.from(parent.context)
            .inflate(R.layout.item_rvsongsl,parent,false)
        return InnerHolder(view)
    }

    override fun onBindViewHolder(holder: InnerHolder, position: Int) {
       val iem=getItem(position)
        holder.name.text=iem.name
        holder.singername.text=iem.ar[0].name

    }


}