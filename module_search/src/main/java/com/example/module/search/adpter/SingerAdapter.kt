package com.example.module.search.adpter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.launcher.ARouter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.module.search.R
import com.example.module.search.adpter.SinglistAdapter.InnerHolder
import com.example.module.search.bean.singer.Artist

class SingerAdapter(val data: List<Artist>) : RecyclerView.Adapter<SingerAdapter.InnerHolder>() {
    class InnerHolder(view: View) : RecyclerView.ViewHolder(view) {
        // val singerPic: ImageView = view.findViewById(R.id.singerImage)
        val singerName: TextView = view.findViewById(R.id.singerName)
        val singImage:ImageView=view.findViewById(R.id.singerImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_singer, parent, false)
        val viewHolder= InnerHolder(view)
        viewHolder.itemView.setOnClickListener {
            Toast.makeText(parent.context,"不好意思，多模块还没做", Toast.LENGTH_SHORT).show()
        }
        return viewHolder
    }

    override fun getItemCount(): Int {
        return data.size
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onBindViewHolder(holder: InnerHolder, position: Int) {
        val item = data[position]
        holder.singerName.text = item.artistName

         Glide.with(holder.itemView.context).load(item.artistAvatarPicUrl).transform(
             CenterCrop(),
             RoundedCorners(30)
         ).into(holder.singImage)
        holder.itemView.setOnClickListener {

        }
    }
}