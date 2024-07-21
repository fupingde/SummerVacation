package com.example.module.ui.activities

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.ImageButton
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.Network.Retrofit
import com.example.module.main.R
import com.example.module.main.databinding.ActivityMusicPlayBinding
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class MusicPlayActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMusicPlayBinding
    private val compositeDisposable = CompositeDisposable()

    private var mediaPlayer: MediaPlayer? = null
    private var songUrl: String? = null

    private lateinit var seekBar: SeekBar
    private lateinit var currentTime: TextView
    private lateinit var totalTime: TextView
    private lateinit var playPauseButton: ImageButton

    private val handler = Handler(Looper.getMainLooper())
    private var isPlaying = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMusicPlayBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 获取Intent中的数据
        val songId = intent.getLongExtra("SONG_ID", -1L)
        val songName = intent.getStringExtra("SONG_NAME")
        val songArtist = intent.getStringExtra("SONG_ARTIST")
        val songPictureUrl = intent.getStringExtra("SONG_PICTUREURL")

        // 检查songId是否有效
        if (songId == -1L) {
            Log.e("MusicPlayActivity", "Invalid Song ID")
            return
        }

        // 设置UI
        binding.songTitle.text = songName
        binding.songArtist.text = songArtist
        seekBar = binding.seekBar
        currentTime = binding.currentTime
        totalTime = binding.totalTime
        playPauseButton = binding.playPauseButton

        // 使用 Glide 加载专辑封面
        Glide.with(this)
            .load(songPictureUrl)
            .into(binding.albumCover)

        // 获取歌曲 URL
        getSongUrl(songId)

        // 设置播放按钮点击事件
        playPauseButton.setOnClickListener {
            if (isPlaying) {
                pauseMusic()
            } else {
                playMusic()
            }
        }
    }

    private fun getSongUrl(songId: Long) {
        Log.d("MusicPlayActivity", "Fetching song URL for song ID: $songId")
        val disposable = Retrofit.apiService.geturl(songId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ songUrlResponse ->
                // 处理 songUrl 数据
                Log.d("MusicPlayActivity", "API response code: ${songUrlResponse.code}")
                songUrlResponse.data.forEach { data ->
                    Log.d("MusicPlayActivity", "Data item: $data")
                }
                val firstUrl = songUrlResponse.data.firstOrNull()
                songUrl = firstUrl?.url
                Log.d("MusicPlayActivity", "Song URL: $songUrl")
                if (songUrl.isNullOrEmpty()) {
                    Log.e("MusicPlayActivity", "No valid URL found in the response.")
                }
            }, { error ->
                // 处理错误
                error.printStackTrace()
                Log.e("MusicPlayActivity", "Error fetching song URL", error)
            })

        compositeDisposable.add(disposable)
    }

    private fun playMusic() {
        songUrl?.let {
            if (mediaPlayer == null) {
                mediaPlayer = MediaPlayer().apply {
                    setDataSource(it)
                    setOnPreparedListener { mp ->
                        mp.start()
                        this@MusicPlayActivity.isPlaying = true
                        playPauseButton.setImageResource(R.drawable.play2) // 切换为暂停图标
                        val duration = mp.duration
                        seekBar.max = duration
                        totalTime.text = formatTime(duration)
                        updateSeekBar()
                    }
                    setOnErrorListener { mp, what, extra ->
                        Log.e("MusicPlayActivity", "MediaPlayer error: what=$what, extra=$extra")
                        true
                    }
                    prepareAsync()
                }
            } else {
                mediaPlayer?.start()
                isPlaying = true
                playPauseButton.setImageResource(R.drawable.play2) // 切换为暂停图标
                updateSeekBar()
            }
        } ?: run {
            Log.e("MusicPlayActivity", "Song URL is null")
        }
    }

    private fun pauseMusic() {
        mediaPlayer?.pause()
        isPlaying = false
        playPauseButton.setImageResource(R.drawable.play) // 切换为播放图标
        handler.removeCallbacks(updateRunnable)
    }

    private fun updateSeekBar() {
        mediaPlayer?.let { mp ->
            seekBar.progress = mp.currentPosition
            currentTime.text = formatTime(mp.currentPosition)
            if (mp.isPlaying) {
                handler.postDelayed(updateRunnable, 1000)
            }
        }
    }

    private val updateRunnable = Runnable {
        updateSeekBar()
    }

    private fun formatTime(milliseconds: Int): String {
        val minutes = TimeUnit.MILLISECONDS.toMinutes(milliseconds.toLong())
        val seconds = TimeUnit.MILLISECONDS.toSeconds(milliseconds.toLong()) % 60
        return String.format("%02d:%02d", minutes, seconds)
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
        mediaPlayer?.release()
        mediaPlayer = null
        handler.removeCallbacks(updateRunnable)
    }
}
