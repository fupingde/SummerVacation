package com.example.module.ui.services

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.Handler
import android.os.IBinder
import android.util.Log
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer

class MusicService : Service() {

    private val binder = MusicBinder()
    private var player: ExoPlayer? = null
    private var _isPlaying: Boolean = false
    private var songUrl: String? = null
    private val handler = Handler()

    var songId: Long = -1L
    var songName: String? = null
    var songArtist: String? = null
    var songPictureUrl: String? = null

    private var dataChangeListener: DataChangeListener? = null

    interface DataChangeListener {
        fun onDataChanged(songId: Long, songName: String?, songArtist: String?, songPictureUrl: String?)
    }

    fun setDataChangeListener(listener: DataChangeListener) {
        dataChangeListener = listener
    }

    fun updateSongData(songId: Long, songName: String?, songArtist: String?, songPictureUrl: String?) {
        this.songId = songId
        this.songName = songName
        this.songArtist = songArtist
        this.songPictureUrl = songPictureUrl
        Log.d("musicservice", "updateSongData: $songId,$songName,$songArtist,$songPictureUrl")
        dataChangeListener?.onDataChanged(songId, songName, songArtist, songPictureUrl)
    }

    override fun onBind(intent: Intent?): IBinder {
        return binder
    }

    override fun onUnbind(intent: Intent?): Boolean {
        return true
    }

    inner class MusicBinder : Binder() {
        fun getService(): MusicService = this@MusicService
    }

    fun playMusic(url: String) {
        if (player == null || songUrl != url) {
            initializeExoPlayer(url)
        } else if (!_isPlaying) {
            player?.playWhenReady = true
            _isPlaying = true
            handler.post(updateRunnable)
        }
    }

    private fun initializeExoPlayer(url: String) {
        player?.release()
        player = ExoPlayer.Builder(this).build().apply {
            setMediaItem(MediaItem.fromUri(url))
            prepare()
            addListener(object : Player.Listener {
                override fun onPlaybackStateChanged(state: Int) {
                    if (state == Player.STATE_READY) {
                        _isPlaying = true
                        playWhenReady = true
                        handler.post(updateRunnable)
                    } else if (state == Player.STATE_ENDED) {
                        _isPlaying = false
                        handler.removeCallbacks(updateRunnable)
                    }
                }

                override fun onPlayerError(error: PlaybackException) {
                    _isPlaying = false
                }
            })
        }
        songUrl = url
    }

    fun pauseMusic() {
        player?.playWhenReady = false
        _isPlaying = false
        handler.removeCallbacks(updateRunnable)
    }

    fun getCurrentPosition(): Int {
        return player?.currentPosition?.toInt() ?: 0
    }

    fun getDuration(): Int {
        return player?.duration?.toInt() ?: 0
    }

    fun isPlaying(): Boolean {
        return _isPlaying
    }

    fun getPlayer(): ExoPlayer? {
        return player
    }

    fun getCurrentSongUrl(): String? {
        return songUrl
    }

    fun isPrepared(): Boolean {
        return player?.playWhenReady ?: false
    }

    fun seekTo(position: Long) {
        player?.seekTo(position)
        player?.playWhenReady = true
        _isPlaying = true
        handler.post(updateRunnable)
    }

    fun change_isplaying() {
        _isPlaying = !_isPlaying
    }

    private val updateRunnable: Runnable = object : Runnable {
        override fun run() {
            player?.let {
                if (_isPlaying) {
                    handler.postDelayed(this, 1000)
                }
            }
        }
    }
}
