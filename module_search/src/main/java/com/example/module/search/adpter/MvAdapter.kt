package com.example.module.search.adpter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.module.search.R
import com.example.module.search.bean.mv.Mv

class MvAdapter() : ListAdapter<Mv, MvAdapter.InnerHolder>(ItemDiffcallback()) {
    class InnerHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.mvName)
        val from: TextView = itemView.findViewById(R.id.mvfrom)
        val mvimage: ImageView = itemView.findViewById(R.id.mvimage)

    }

    class ItemDiffcallback : DiffUtil.ItemCallback<Mv>() {
        override fun areItemsTheSame(oldItem: Mv, newItem: Mv): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Mv, newItem: Mv): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_mv, parent, false)
        val viewHolder = InnerHolder(view)
        viewHolder.itemView.setOnClickListener {
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: InnerHolder, position: Int) {
        val item = getItem(position)
        holder.name.text = item.name
        holder.from.text = item.alias?.get(0)
        Glide.with(holder.itemView.context)
            .load(item.cover).into(holder.mvimage)

    }


}