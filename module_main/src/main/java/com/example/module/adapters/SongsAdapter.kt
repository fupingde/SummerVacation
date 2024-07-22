package com.example.module.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.Network.Bean.Song2
import com.example.module.main.databinding.ItemSongBinding

class SongsAdapter(
    private val songs: List<Song2>,
    private val onItemClick: (song: Song2, songs: List<Song2>, position: Int) -> Unit
) : RecyclerView.Adapter<SongsAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemSongBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(song: Song2, position: Int) {
            binding.songIndex.text = (position + 1).toString()
            binding.songName.text = song.name
            binding.songArtist.text = song.ar.joinToString(", ") { it.name }

            binding.root.setOnClickListener {
                onItemClick(song, songs, position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemSongBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(songs[position], position)
    }

    override fun getItemCount(): Int = songs.size
}
