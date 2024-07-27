package com.example.module.adapters


import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.Network.Bean.CollctedSong
import com.example.Network.Bean.Song2
import com.example.module.main.databinding.ItemSongBinding
import com.example.module.ui.MusicPlayActivity


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
            val intent = Intent(context, MusicPlayActivity::class.java).apply {
                putExtra("SONG_ID", song.id)
                putExtra("SONG_NAME", song.name)
                putExtra("SONG_ARTIST", song.artist)
                putExtra("SONG_PICTUREURL", song.songPictureUrl)
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return songs.size
    }

}
