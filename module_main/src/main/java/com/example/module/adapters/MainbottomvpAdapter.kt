package com.example.module.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.launcher.ARouter
import com.bumptech.glide.Glide
import com.example.network.Bean.Result2
import com.example.module.main.R

class MainbottomvpAdapter(private val context: Context, private val songs: List<Result2>) : RecyclerView.Adapter<MainbottomvpAdapter.NewSongViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewSongViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_new_song, parent, false)
        return NewSongViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewSongViewHolder, position: Int) {
        val song = songs[position]
        holder.titleTextView.text = song.name
        holder.artistTextView.text = song.song.artists.joinToString(", ") { it.name }
        Glide.with(holder.imageView.context)
            .load(song.picUrl)
            .into(holder.imageView)

        holder.itemView.setOnClickListener {
            // 使用 ARouter 传递数据
            ARouter.getInstance().build("/main/MusicPlayActivity")
                .withLong("songId", song.song.id)
                .withString("songName", song.song.name)
                .withString("artistName", song.song.artists.firstOrNull()?.name)
                .withString("songImageUrl", song.picUrl)
                .navigation()
        }
    }

    override fun getItemCount(): Int {
        return songs.size
    }

    class NewSongViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val artistTextView: TextView = itemView.findViewById(R.id.artistTextView)
    }
}
