package com.example.module.ui.services

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.Handler
import android.os.IBinder
import android.util.Log
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player

class MusicService : Service() {

    private val binder = MusicBinder()
    private var exoPlayer: ExoPlayer? = null
    private var _isPlaying: Boolean = false
    private var songUrl: String? = null
    private val handler = Handler()

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
        if (exoPlayer == null || songUrl != url) {
            initializeExoPlayer(url)
        } else if (!_isPlaying) {
            exoPlayer?.play()
            _isPlaying = true
            handler.post(updateRunnable)
        }
    }

    private fun initializeExoPlayer(url: String) {
        Log.d("MusicService", "initializeExoPlayer")
        exoPlayer?.release()
        exoPlayer = ExoPlayer.Builder(this).build().apply {
            setMediaItem(MediaItem.fromUri(url))
            prepare()
            addListener(object : Player.Listener {
                override fun onPlaybackStateChanged(state: Int) {
                    if (state == Player.STATE_READY) {
                        _isPlaying = true
                        play()
                        handler.post(updateRunnable)
                    } else if (state == Player.STATE_ENDED) {
                        _isPlaying = false
                        handler.removeCallbacks(updateRunnable)
                    }
                }

                override fun onPlayerError(error: PlaybackException) {
                    Log.e("MusicService", "Player error: ${error.message}")
                    _isPlaying = false
                }
            })
        }
        songUrl = url
    }

    fun pauseMusic() {
        Log.d("MusicService", "pauseMusic")
        exoPlayer?.pause()
        _isPlaying = false
        handler.removeCallbacks(updateRunnable)
        Log.d("MusicService", "music paused")
    }

    fun getCurrentPosition(): Int {
        return exoPlayer?.currentPosition?.toInt() ?: 0
    }

    fun getDuration(): Int {
        return exoPlayer?.duration?.toInt() ?: 0
    }

    fun isPlaying(): Boolean {
        return _isPlaying
    }

    fun getCurrentSongUrl(): String? {
        return songUrl
    }

    fun isPrepared(): Boolean {
        return exoPlayer?.playWhenReady ?: false
    }

    fun getExoPlayer(): ExoPlayer? {
        return exoPlayer
    }

    fun change_isplaying() {
        _isPlaying = !_isPlaying
    }

    private val updateRunnable: Runnable = object : Runnable {
        override fun run() {
            exoPlayer?.let {
                if (_isPlaying) {
                    handler.postDelayed(this, 1000)
                }
            }
        }
    }
}
