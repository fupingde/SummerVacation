package com.example.module.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.Network.Bean.Playlistr
import com.example.module.main.R
import com.example.module.ui.activities.SongListActivity

class RvListAdapter():ListAdapter<Playlistr,RvListAdapter.InnerHolder> (ItemDiffcallback()){
    class ItemDiffcallback : DiffUtil.ItemCallback<Playlistr>() {
        override fun areItemsTheSame(oldItem: Playlistr, newItem: Playlistr): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Playlistr, newItem: Playlistr): Boolean {
            return oldItem == newItem

        }
    }
    class InnerHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val listImage: ImageView = itemView.findViewById(R.id.ListrvImage)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.item_listrv,parent,false)
        return InnerHolder(view)

    }

    override fun onBindViewHolder(holder: InnerHolder, position: Int) {
     val item =getItem(position)

        Glide.with(holder.itemView.context).load(item.coverImgUrl)
            .transform(CenterCrop(), RoundedCorners(30))
            .into(holder.listImage)
   holder.itemView.setOnClickListener {
       val intent = Intent(holder.listImage.context, SongListActivity::class.java).apply {
           putExtra("playlistId", item.id)
           putExtra("playlistName", item.name)
           putExtra("playlistImageUrl", item.coverImgUrl)
       }
      holder.itemView.context.startActivity(intent)

   }


    }


}