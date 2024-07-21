package com.example.module.search.adpter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.module.search.R
import com.example.module.search.adpter.SongAdapter.ViewHolder
import com.example.module.search.bean.Album
import com.example.module.search.bean.Album1

class SinglistAdapter() :ListAdapter<Album1,SinglistAdapter.InnerHolder>(ItemDiffcallback()) {
    class InnerHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.singlistName)
        val singername: TextView = itemView.findViewById(R.id.singer_singlist)
        val  image :ImageView=itemView.findViewById(R.id.singlistImage)

    }


    class ItemDiffcallback : DiffUtil.ItemCallback<Album1>() {
        override fun areItemsTheSame(oldItem: Album1, newItem: Album1): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Album1, newItem: Album1): Boolean {
            return oldItem == newItem

        }


    }

    @SuppressLint("SuspiciousIndentation")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerHolder {
      val view=LayoutInflater.from(parent.context).inflate(R.layout.item_singlist,parent,false)
        val viewHolder= InnerHolder(view)
        viewHolder.itemView.setOnClickListener {
            Toast.makeText(parent.context,"不好意思，多模块还没做", Toast.LENGTH_SHORT).show()
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: InnerHolder, position: Int) {
        val item=getItem(position)
        holder.name.text=item.name
        holder.singername.text=item.artist.name
        Glide.with(holder.itemView.context)
            .load(item.picUrl).transform(CenterCrop(),RoundedCorners(30))
            .into(holder.image)
      //  Log.d("SinglistAdapter", "onBindViewHolder: " + item.name + " - " + item.artist.name);
    }
}