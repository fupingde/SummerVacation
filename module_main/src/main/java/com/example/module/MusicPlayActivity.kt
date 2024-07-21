package com.example.module.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.Network.Bean.Song2
import com.example.module.main.databinding.ActivityMusicPlayBinding
import java.io.Serializable

class MusicPlayActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMusicPlayBinding
    private lateinit var songs: List<Song2>
    private var currentSongIndex: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMusicPlayBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = intent
        songs = intent.getSerializableExtra("songs") as List<Song2>
        currentSongIndex = intent.getIntExtra("currentSongIndex", 0)

        // 显示当前播放的歌曲信息
        displaySongInfo(currentSongIndex)
    }

    private fun displaySongInfo(index: Int) {
        val song = songs[index]
        binding.songTitle.text = song.name
        binding.songArtist.text = song.ar.joinToString(", ") { it.name }

        // 使用 Glide 或其他库加载专辑封面
        Glide.with(this)
            .load(song.al.picUrl)
            .into(binding.albumCover)
    }
}
