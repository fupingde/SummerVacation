package com.example.module.broadcast.adapyer

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.module.broadcast.databinding.ItemLyricBinding

class MvPagerAdapte : RecyclerView.Adapter<MvPagerAdapte.LyricsViewHolder>() {

    private val lyrics = listOf("Lyric 1", "Lyric 2", "Lyric 3") // Sample data

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LyricsViewHolder {
        val binding = ItemLyricBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LyricsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LyricsViewHolder, position: Int) {
        holder.bind(lyrics[position])
    }

    override fun getItemCount(): Int = lyrics.size

    inner class LyricsViewHolder(private val binding: ItemLyricBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(lyric: String) {
            binding.lyricText.text = lyric
        }
    }
}