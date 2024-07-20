package com.example.module.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.Network.Bean.Songs
import com.example.module.main.databinding.ItemSongBinding

class SongsAdapter(private val songs: Songs) : RecyclerView.Adapter<SongsAdapter.SongViewHolder>() {

    inner class SongViewHolder(val binding: ItemSongBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val binding = ItemSongBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SongViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        val song = songs.songs[position]
        holder.binding.songIndex.text = (position + 1).toString()
        Log.d("recyclerview", "$song")
        holder.binding.songName.text = song.name
        holder.binding.songArtist.text = song.ar.joinToString(", ") { it.name }

        // 你可以在这里设置ImageButton的点击事件处理器
        holder.binding.songButton.setOnClickListener {
            // 处理按钮点击事件
        }
    }

    override fun getItemCount(): Int = songs.songs.size
}
