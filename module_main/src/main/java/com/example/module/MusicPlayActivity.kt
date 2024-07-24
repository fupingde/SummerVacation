package com.example.module.ui.activities

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.util.Log
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.Network.Retrofit
import com.example.module.main.R
import com.example.module.main.databinding.ActivityMusicPlayBinding
import com.example.module.ui.MainActivity
import com.example.module.ui.services.MusicService
import com.example.module.ui.viewmodel.SongViewModel
import com.example.module.ui.viewmodel.ViewModelSingleton
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class MusicPlayActivity : AppCompatActivity() {

    private lateinit var songViewModel: SongViewModel
    private lateinit var binding: ActivityMusicPlayBinding
    private var musicService: MusicService? = null
    private var isBound = false
    private var currentSongUrl: String? = null
    private val handler = Handler(Looper.getMainLooper())
    private val compositeDisposable = CompositeDisposable()
    private var songUrl: String? = null

    private var songId: Long = -1L
    private var songName: String? = null
    private var artistName: String? = null
    private var songImageUrl: String? = null
    private var rotationAngle = 0f
    private val rotationHandler = Handler(Looper.getMainLooper())
    private val rotationRunnable: Runnable = object : Runnable {
        override fun run() {
            binding.albumCover.rotation = rotationAngle
            rotationAngle += 2f
            if (rotationAngle >= 360f) {
                rotationAngle = 0f
            }
            rotationHandler.postDelayed(this, 50)
        }
    }

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as MusicService.MusicBinder
            musicService = binder.getService()
            isBound = true
            updateUI()
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            isBound = false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMusicPlayBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 使用单例类获取SongViewModel实例
        songViewModel = ViewModelSingleton.getSongViewModel(application)
        songId = intent.getLongExtra("SONG_ID", -1L)
        songName = intent.getStringExtra("SONG_NAME") ?: ""
        artistName = intent.getStringExtra("SONG_ARTIST") ?: ""
        songImageUrl = intent.getStringExtra("SONG_PICTUREURL") ?: ""

        binding.songTitle.text = songName
        binding.songArtist.text = artistName
        Glide.with(this).load(songImageUrl).into(binding.albumCover)

        binding.playPauseButton.setOnClickListener {
            handlePlayPause()
        }

        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    musicService?.getMediaPlayer()?.seekTo(progress)
                    binding.currentTime.text = formatTime(progress)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        bindAndStartService()
        getSongUrl(songId)
    }

    private fun bindAndStartService() {
        Intent(this, MusicService::class.java).also { intent ->
            startService(intent)
            val success = bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
            if (!success) {
                Handler(Looper.getMainLooper()).postDelayed({
                    bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
                }, 1000)
            }
        }
    }

    private fun getSongUrl(songId: Long) {
        Log.d("MusicPlayActivity", "Fetching song URL for song ID: $songId")
        val disposable = Retrofit.apiService.geturl(songId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ songUrlResponse ->
                Log.d("MusicPlayActivity", "API response code: ${songUrlResponse.code}")
                songUrlResponse.data.forEach { data ->
                    Log.d("MusicPlayActivity", "Data item: $data")
                }
                val firstUrl = songUrlResponse.data.firstOrNull()
                songUrl = firstUrl?.url
                Log.d("MusicPlayActivity", "Song URL: $songUrl")
                if (songUrl.isNullOrEmpty()) {
                    Log.e("MusicPlayActivity", "No valid URL found in the response.")
                } else {
                    currentSongUrl = songUrl
                    if (!isBound) {
                        Log.d("MusicPlayActivity", "Attempting to bind service again after fetching URL")
                        bindAndStartService()
                    }
                    updateUI()
                }
            }, { error ->
                error.printStackTrace()
                Log.e("MusicPlayActivity", "Error fetching song URL", error)
            })

        compositeDisposable.add(disposable)
    }

    private fun updateUI() {
        if (isBound) {
            val isPlaying = musicService?.isPlaying() == true
            val currentUrl = musicService?.getCurrentSongUrl()
            if (currentUrl == currentSongUrl) {
                if (isPlaying) {
                    binding.playPauseButton.setImageResource(R.drawable.play)
                    rotationHandler.post(rotationRunnable)
                } else {
                    binding.playPauseButton.setImageResource(R.drawable.pause)
                    rotationHandler.removeCallbacks(rotationRunnable)
                }
                binding.seekBar.max = musicService?.getDuration() ?: 0
                binding.seekBar.progress = musicService?.getCurrentPosition() ?: 0
                binding.totalTime.text = formatTime(musicService?.getDuration() ?: 0)
                startSeekBarUpdate()
            } else {
                binding.playPauseButton.setImageResource(R.drawable.pause)
                binding.seekBar.progress = 0
                binding.currentTime.text = "00:00"
                binding.totalTime.text = "00:00"
                rotationHandler.removeCallbacks(rotationRunnable)
            }
        }
    }

    private fun handlePlayPause() {
        Log.d("MusicPlayActivity", "button is clicked.")
        if (isBound) {
            Log.d("MusicPlayActivity", "isplaying:${musicService?.isPlaying()}")
            if (musicService?.isPlaying() == true) {
                currentSongUrl?.let { url ->
                    if (musicService?.getCurrentSongUrl() == url){
                        musicService?.pauseMusic()
                        binding.playPauseButton.setImageResource(R.drawable.pause)
                        handler.removeCallbacks(updateRunnable)
                        rotationHandler.removeCallbacks(rotationRunnable)
                    }else{
                        // 停止当前正在播放的音乐
                        musicService?.pauseMusic()
                        handler.removeCallbacks(updateRunnable)
                        rotationHandler.removeCallbacks(rotationRunnable)
                        // 更新当前歌曲URL
                        currentSongUrl = url
                        // 播放新歌曲
                        musicService?.playMusic(url)
                        binding.playPauseButton.setImageResource(R.drawable.play)
                        startSeekBarUpdate()
                        rotationHandler.post(rotationRunnable)
                        val intent = Intent(this, MainActivity::class.java).apply {
                            putExtra("SONG_ID", songId)
                            putExtra("SONG_NAME", songName)
                            putExtra("SONG_ARTIST", artistName)
                            putExtra("SONG_PICTUREURL", songImageUrl)
                        }
                        songViewModel.updateSongData(songId, songName?:"",artistName?:"", songImageUrl?:"")
                        Log.d("SongListActivity", "SongViewModel updated with URL: $songId, $songName,$artistName$songImageUrl")
                    }
                }

                Log.d("MusicPlayActivity", "pause music")


            } else {
                currentSongUrl?.let { url ->
                    if (musicService?.getCurrentSongUrl() == url) {
                        Log.d("MusicPlayActivity", "getCurrentSongUrl() == url:$url")
                        musicService?.playMusic(url)
                        binding.playPauseButton.setImageResource(R.drawable.play)
                        startSeekBarUpdate()
                        rotationHandler.post(rotationRunnable)
                        val intent = Intent(this, MainActivity::class.java).apply {
                            putExtra("SONG_ID", songId)
                            putExtra("SONG_NAME", songName)
                            putExtra("SONG_ARTIST", artistName)
                            putExtra("SONG_PICTUREURL", songImageUrl)
                        }
                        songViewModel.updateSongData(songId, songName?:"",artistName?:"", songImageUrl?:"")
                        Log.d("SongListActivity", "SongViewModel updated with URL: $songId, $songName,$artistName$songImageUrl")
                    } else {
                        Log.d("MusicPlayActivity", "getCurrentSongUrl() != url,url:$url,currenturl:${musicService?.getCurrentSongUrl()}")
                        musicService?.playMusic(url)
                        musicService?.change_isplaying()
                        binding.playPauseButton.setImageResource(R.drawable.play)
                        musicService?.getMediaPlayer()?.setOnPreparedListener { mp ->
                            binding.seekBar.max = mp.duration
                            binding.totalTime.text = formatTime(mp.duration)
                            mp.start()
                            startSeekBarUpdate()
                            rotationHandler.post(rotationRunnable)
                        }
                        val intent = Intent(this, MainActivity::class.java).apply {
                            putExtra("SONG_ID", songId)
                            putExtra("SONG_NAME", songName)
                            putExtra("SONG_ARTIST", artistName)
                            putExtra("SONG_PICTUREURL", songImageUrl)
                        }
                        songViewModel.updateSongData(songId, songName?:"",artistName?:"", songImageUrl?:"")
                        Log.d("SongListActivity", "SongViewModel updated with URL: $songId, $songName,$artistName$songImageUrl")
                    }
                }
            }
        } else {
            bindAndStartService()
        }
    }

    private fun startSeekBarUpdate() {
        handler.post(updateRunnable)
    }

    private val updateRunnable: Runnable = object : Runnable {
        override fun run() {
            musicService?.let {
                binding.seekBar.progress = it.getCurrentPosition()
                binding.currentTime.text = formatTime(it.getCurrentPosition())
                handler.postDelayed(this, 1000)
            }
        }
    }

    private fun formatTime(milliseconds: Int): String {
        val minutes = TimeUnit.MILLISECONDS.toMinutes(milliseconds.toLong())
        val seconds = TimeUnit.MILLISECONDS.toSeconds(milliseconds.toLong()) % 60
        return String.format("%02d:%02d", minutes, seconds)
    }

    override fun onStart() {
        super.onStart()
        if (isBound) {
            updateUI()
        } else {
            bindAndStartService()
        }
    }

    override fun onStop() {
        super.onStop()
        if (isBound) {
            unbindService(serviceConnection)
            isBound = false
        }
        handler.removeCallbacks(updateRunnable)
        rotationHandler.removeCallbacks(rotationRunnable)
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }
}
