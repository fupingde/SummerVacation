package com.example.module.ui.services

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.Handler
import android.os.IBinder
import android.util.Log

class MusicService : Service() {

    private val binder = MusicBinder()
    private var mediaPlayer: MediaPlayer? = null
    private var _isPlaying: Boolean = false
    private var songUrl: String? = null
    private var isPrepared: Boolean = false
    private val handler =Handler()

    override fun onBind(intent: Intent?): IBinder {
        Log.d("MusicService", "Service bound")
        return binder
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.d("MusicService", "Service unbound")
        return true
    }

    inner class MusicBinder : Binder() {
        fun getService(): MusicService = this@MusicService
    }

    fun playMusic(url: String) {
        if (mediaPlayer == null || songUrl != url) {
            initializeMediaPlayer(url)
        } else if (!_isPlaying && isPrepared) {
            mediaPlayer?.start()
            _isPlaying = true
            handler.post(updateRunnable)
        }
    }

    private fun initializeMediaPlayer(url: String) {
        Log.d("MusicPlayActivity", "initializeMediaPlayer")
        Log.d("MusicPlayActivity", "initializeMediaPlayer  isplaying:$_isPlaying")
        mediaPlayer?.release()
        mediaPlayer = MediaPlayer().apply {
            setDataSource(url)
            setOnPreparedListener { mp ->
                isPrepared = true
                mp.start()
                _isPlaying = true
                handler.post(updateRunnable)
            }
            setOnErrorListener { mp, what, extra ->
                true
            }
            setOnCompletionListener {
                _isPlaying = false
                handler.removeCallbacks(updateRunnable)
            }
            prepareAsync()
        }
        songUrl = url
        isPrepared = true
    }

    fun pauseMusic() {
        Log.d("MusicPlayActivity", "pauseMusic")
        mediaPlayer?.pause()
        _isPlaying = false
        handler.removeCallbacks(updateRunnable)
        Log.d("MusicPlayActivity", "music pause")
    }


    fun getCurrentPosition(): Int {
        return mediaPlayer?.currentPosition ?: 0
    }

    fun getDuration(): Int {
        return mediaPlayer?.duration ?: 0
    }

    fun isPlaying(): Boolean {
        return _isPlaying
    }

    fun getCurrentSongUrl(): String? {
        return songUrl
    }

    fun isPrepared(): Boolean {
        return isPrepared
    }

    fun getMediaPlayer(): MediaPlayer? {
        return mediaPlayer
    }

    fun change_isplaying(){
        if (_isPlaying==false)_isPlaying=true
        else _isPlaying=false
    }
    private val updateRunnable: Runnable = object : Runnable {
        override fun run() {
            mediaPlayer?.let {
                if (_isPlaying) {
                    handler.postDelayed(this, 1000)
                }
            }
        }
    }
}
