package com.example.module.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.launcher.ARouter
import com.example.Network.Bean.CollctedSong
import com.example.module.main.databinding.ItemSongBinding

class MydownloadrvAdapter(private val context: Context, private val songs: List<CollctedSong>) : RecyclerView.Adapter<MydownloadrvAdapter.SongViewHolder>() {

    class SongViewHolder(val binding: ItemSongBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val binding = ItemSongBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SongViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        val song = songs[position]
        holder.binding.songIndex.text = (position + 1).toString()
        holder.binding.songName.text = song.name
        holder.binding.songArtist.text = song.artist

        holder.itemView.setOnClickListener {
            // 使用 ARouter 传递数据
            ARouter.getInstance().build("/main/MusicPlayActivity")
                .withLong("songId", song.id)
                .withString("songName", song.name)
                .withString("artistName", song.artist)
                .withString("songImageUrl", song.songPictureUrl)
                .navigation()
        }
    }

    override fun getItemCount(): Int {
        return songs.size
    }
}
