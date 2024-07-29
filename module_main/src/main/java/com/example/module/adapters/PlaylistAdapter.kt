package com.example.module.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.network.Bean.Song2
import com.example.module.main.databinding.ItemPlaylistBinding

class PlaylistAdapter(
    private val songs: List<Song2>,
    private val onItemClick: (Song2, List<Song2>, Int) -> Unit
) : RecyclerView.Adapter<PlaylistAdapter.PlaylistViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistViewHolder {
        val binding = ItemPlaylistBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PlaylistViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PlaylistViewHolder, position: Int) {
        val song = songs[position]
        holder.bind(song)
        holder.itemView.setOnClickListener {
            onItemClick(song, songs, position)
        }
    }

    override fun getItemCount(): Int = songs.size

    class PlaylistViewHolder(private val binding: ItemPlaylistBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(song: Song2) {
            binding.songName.text = song.name
            binding.songArtist.text = song.ar.joinToString(", ") { it.name }
        }
    }
}
