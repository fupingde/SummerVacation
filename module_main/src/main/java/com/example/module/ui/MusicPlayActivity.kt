package com.example.module.ui

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.util.Log
import android.widget.SeekBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.bumptech.glide.Glide
import com.example.network.Bean.Song2
import com.example.network.api.Retrofit
import com.example.module.database.MyDatabaseHelper
import com.example.module.main.R
import com.example.module.main.databinding.ActivityMusicPlayBinding
import com.example.module.ui.services.MusicService
import com.example.module.ui.viewmodel.SongViewModel
import com.example.module.ui.fragments.PlaylistBottomSheetFragment
import com.example.module.ui.viewmodel.SongListViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
@Route(path = "/main/MusicPlayActivity")
class MusicPlayActivity : AppCompatActivity() {

    data class LyricLine(val time: Int, val text: String)

    private var lyrics: List<LyricLine> = emptyList()
    private lateinit var songViewModel: SongViewModel
    private lateinit var songListViewModel: SongListViewModel
    private lateinit var binding: ActivityMusicPlayBinding
    private var musicService: MusicService? = null
    private var isBound = false
    private var currentSongUrl: String? = null
    private val handler = Handler(Looper.getMainLooper())
    private val compositeDisposable = CompositeDisposable()
    private var songUrl: String? = null

    @Autowired(name = "songId")
    @JvmField
    var songId: Long = 0

    @Autowired(name = "songName")
    @JvmField
    var songName: String = null.toString()

    @Autowired(name = "artistName")
    @JvmField
    var artistName: String = null.toString()

    @Autowired(name = "songImageUrl")
    @JvmField
    var songImageUrl: String = null.toString()
    private var playlistSongs: List<Song2> = emptyList()
    private var playOrderState = 0 // 0: playorder, 1: cycle, 2: random
    private var isTransitioningToNextSong = false
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
        ARouter.getInstance().inject(this)

        // 使用单例类获取SongViewModel实例
        songViewModel = ViewModelSingleton.getSongViewModel(application)
        songListViewModel = ViewModelProvider(this).get(SongListViewModel::class.java)
        if (intent.getLongExtra("SONG_ID", -1L) != -1L) {
            songId = intent.getLongExtra("SONG_ID", -1L)
            songName = intent.getStringExtra("SONG_NAME") ?: ""
            artistName = intent.getStringExtra("SONG_ARTIST") ?: ""
            songImageUrl = intent.getStringExtra("SONG_PICTUREURL") ?: ""
        }
        playlistSongs = intent.getSerializableExtra("PLAYLIST_SONGS") as? ArrayList<Song2> ?: ArrayList()
        // 添加日志以查看传递的歌曲数据
        Log.d("MusicPlayActivity", "Received Song ID: $songId")
        Log.d("MusicPlayActivity", "Received Song Name: $songName")
        Log.d("MusicPlayActivity", "Received Artist Name: $artistName")
        Log.d("MusicPlayActivity", "Received Song Image URL: $songImageUrl")

        binding.songTitle.text = songName
        binding.songArtist.text = artistName
        Glide.with(this).load(songImageUrl).into(binding.albumCover)

        // 设置收藏按钮的初始图片
        val dbHelper = MyDatabaseHelper(this)
        if (dbHelper.isSongCollected(songId)) {
            binding.collectbutton.setImageResource(R.drawable.collected)
        } else {
            binding.collectbutton.setImageResource(R.drawable.shoucang)
        }

        binding.playPauseButton.setOnClickListener {
            handlePlayPause()
        }

        binding.collectbutton.setOnClickListener {
            handleCollectButtonClick()
        }

        binding.downloadbutton.setOnClickListener {
            handleDownloadButtonClick()
        }
        // 为 previousButton 和 nextButton 添加点击事件
        binding.previousButton.setOnClickListener {
            playPreviousSong()
        }

        binding.nextButton.setOnClickListener {
            playNextSong()
        }
        binding.playorder.setImageResource(R.drawable.playorder)
        binding.playorder.setOnClickListener {
            playOrderState = (playOrderState + 1) % 3 // 循环更新状态
            updatePlayOrderButton()
        }

        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    musicService?.getPlayer()?.seekTo(progress.toLong())
                    binding.currentTime.text = formatTime(progress)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                // 暂停音乐
                musicService?.pauseMusic()
                // 停止旋转任务
                rotationHandler.removeCallbacks(rotationRunnable)
                Log.d("MusicPlayActivity", "SeekBar touch started")
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                // 继续播放音乐
                currentSongUrl?.let { url ->
                    musicService?.playMusic(url)
                    binding.playPauseButton.setImageResource(R.drawable.play)
                    startSeekBarUpdate()
                    // 启动旋转任务前确保移除之前的任务
                    rotationHandler.removeCallbacks(rotationRunnable)
                    rotationHandler.post(rotationRunnable)
                }
                Log.d("MusicPlayActivity", "SeekBar touch stopped")
            }
        })

        // 绑定 playlist 按钮并设置点击事件
        binding.playlist.setOnClickListener {
            Log.d("MusicPlayActivity", "Playlist button clicked")
            Log.d("MusicPlayActivity", "playlistSongs size: ${playlistSongs.size}")
            playlistSongs.forEach { song ->
                Log.d("MusicPlayActivity", "Song: ${song.name} by ${song.ar.joinToString(", ") { it.name }}")
            }

            val bottomSheetFragment = PlaylistBottomSheetFragment.newInstance(playlistSongs)
            bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)
            Log.d("MusicPlayActivity", "Playlist button clicked and PlaylistBottomSheetFragment shown")
        }

        bindAndStartService()
        getSongUrl(songId)
        getLyrics(songId)
    }

    private fun handleDownloadButtonClick() {
        val dbHelper = MyDatabaseHelper(this)
        if (dbHelper.isSongDownloaded(songId)) {
            Toast.makeText(this, "歌曲已下载", Toast.LENGTH_SHORT).show()
        } else {
            dbHelper.insertDownloadedSong(songId, songName ?: "", artistName ?: "", songImageUrl ?: "")
            Toast.makeText(this, "歌曲下载成功", Toast.LENGTH_SHORT).show()
        }
    }

    private fun handleCollectButtonClick() {
        val dbHelper = MyDatabaseHelper(this)
        if (dbHelper.isSongCollected(songId)) {
            dbHelper.deleteCollectedSong(songId)
            binding.collectbutton.setImageResource(R.drawable.shoucang)
            Toast.makeText(this, "取消收藏", Toast.LENGTH_SHORT).show()
        } else {
            dbHelper.insertCollectedSong(songId, songName ?: "", artistName ?: "", songImageUrl ?: "")
            binding.collectbutton.setImageResource(R.drawable.collected)
            Toast.makeText(this, "收藏成功", Toast.LENGTH_SHORT).show()
        }
    }

    private fun bindAndStartService() {
        Intent(this, MusicService::class.java).also { intent ->
            startService(intent)
            bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
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
                        Log.d(
                            "MusicPlayActivity",
                            "Attempting to bind service again after fetching URL"
                        )
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
                    // 启动旋转任务前确保移除之前的任务
                    rotationHandler.removeCallbacks(rotationRunnable)
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
                binding.seekBar.max = musicService?.getDuration() ?: 0
                binding.totalTime.text = formatTime(getMusicDuration(songUrl ?: ""))
            }
        }
    }

    private fun getMusicDuration(url: String, callback: (Int) -> Unit) {
        val player = ExoPlayer.Builder(this).build().apply {
            setMediaItem(MediaItem.fromUri(url))
            prepare()
            addListener(object : Player.Listener {
                override fun onPlaybackStateChanged(state: Int) {
                    if (state == Player.STATE_READY) {
                        val duration = duration.toInt()
                        callback(duration)
                        release()
                    } else if (state == Player.STATE_IDLE || state == Player.STATE_ENDED || state == Player.STATE_BUFFERING) {
                        release()
                    }
                }

                override fun onPlayerError(error: PlaybackException) {
                    Log.e("MusicDuration", "Player error: ${error.message}")
                    callback(-1)
                    release()
                }
            })
        }
    }


    private fun getMusicDuration(url: String): Int {
        val mediaPlayer = MediaPlayer()
        return try {
            mediaPlayer.setDataSource(url)
            mediaPlayer.prepare()  // 这里是同步方法，会阻塞直到准备完成
            val duration = mediaPlayer.duration
            mediaPlayer.release()
            duration
        } catch (e: Exception) {
            Log.e("MusicDuration", "Error: ${e.message}")
            mediaPlayer.release()
            -1
        }
    }

    private fun handlePlayPause() {
        Log.d("MusicPlayActivity", "button is clicked.")
        if (isBound) {
            Log.d("MusicPlayActivity", "isplaying:${musicService?.isPlaying()}")
            if (musicService?.isPlaying() == true) {
                currentSongUrl?.let { url ->
                    if (musicService?.getCurrentSongUrl() == url) {
                        musicService?.pauseMusic()
                        binding.playPauseButton.setImageResource(R.drawable.pause)
                        handler.removeCallbacks(updateRunnable)
                        rotationHandler.removeCallbacks(rotationRunnable)
                        updateUI()
                    } else {
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
                        Intent(this, MainActivity::class.java).apply {
                            putExtra("SONG_ID", songId)
                            putExtra("SONG_NAME", songName)
                            putExtra("SONG_ARTIST", artistName)
                            putExtra("SONG_PICTUREURL", songImageUrl)
                        }
                        songViewModel.updateSongData(
                            songId,
                            songName ?: "",
                            artistName ?: "",
                            songImageUrl ?: ""
                        )
                        Log.d(
                            "SongListActivity",
                            "SongViewModel updated with URL: $songId, $songName,$artistName$songImageUrl"
                        )
                    }
                }
                Log.d("MusicPlayActivity", "pause music")
            } else {
                if (playlistSongs.isEmpty() && musicService?.getCurrentSongUrl() == currentSongUrl) {
                    Toast.makeText(this, "这已经是最后一首了", Toast.LENGTH_SHORT).show()
                } else {
                    currentSongUrl?.let { url ->
                        if (musicService?.getCurrentSongUrl() == url) {
                            Log.d("MusicPlayActivity", "getCurrentSongUrl() == url:$url")
                            musicService?.playMusic(url)
                            binding.playPauseButton.setImageResource(R.drawable.play)
                            startSeekBarUpdate()
                            rotationHandler.post(rotationRunnable)
                            Intent(this, MainActivity::class.java).apply {
                                putExtra("SONG_ID", songId)
                                putExtra("SONG_NAME", songName)
                                putExtra("SONG_ARTIST", artistName)
                                putExtra("SONG_PICTUREURL", songImageUrl)
                            }
                            songViewModel.updateSongData(
                                songId,
                                songName ?: "",
                                artistName ?: "",
                                songImageUrl ?: ""
                            )
                            Log.d(
                                "SongListActivity",
                                "SongViewModel updated with URL: $songId, $songName,$artistName$songImageUrl"
                            )
                        } else {
                            Log.d(
                                "MusicPlayActivity",
                                "getCurrentSongUrl() != url,url:$url,currenturl:${musicService?.getCurrentSongUrl()}"
                            )
                            musicService?.playMusic(url)
                            musicService?.change_isplaying()
                            binding.playPauseButton.setImageResource(R.drawable.play)
                            musicService?.getPlayer()?.addListener(object : Player.Listener {
                                override fun onPlaybackStateChanged(playbackState: Int) {
                                    if (playbackState == Player.STATE_READY) {
                                        binding.seekBar.max = musicService?.getDuration() ?: 0
                                        Log.d("duration", "Duration:${musicService?.getDuration()}")
                                        binding.totalTime.text = formatTime(musicService?.getDuration() ?: 0)
                                        startSeekBarUpdate()
                                        rotationHandler.post(rotationRunnable)
                                    }
                                }
                            })
                            Intent(this, MainActivity::class.java).apply {
                                putExtra("SONG_ID", songId)
                                putExtra("SONG_NAME", songName)
                                putExtra("SONG_ARTIST", artistName)
                                putExtra("SONG_PICTUREURL", songImageUrl)
                            }
                            songViewModel.updateSongData(
                                songId,
                                songName ?: "",
                                artistName ?: "",
                                songImageUrl ?: ""
                            )
                            Log.d(
                                "SongListActivity",
                                "SongViewModel updated with URL: $songId, $songName,$artistName$songImageUrl"
                            )
                        }
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
                val currentPosition = it.getCurrentPosition()
                val duration = it.getDuration()
                binding.seekBar.progress = currentPosition
                binding.currentTime.text = formatTime(currentPosition)
                updateLyricsView(currentPosition)

                if (duration > 0 && currentPosition >= duration - 1000 && !isTransitioningToNextSong) { // 进度条接近最大值时
                    isTransitioningToNextSong = true
                    Log.d("MusicPlayActivity", "Transitioning to next song")

                    if (playlistSongs.isEmpty()) {
                        Log.d("MusicPlayActivity", "Playlist is empty, stopping music")
                        musicService?.pauseMusic()
                        binding.playPauseButton.setImageResource(R.drawable.pause)
                        rotationHandler.removeCallbacks(rotationRunnable)
                        handler.removeCallbacks(this)
                        isTransitioningToNextSong = false
                    } else {
                        playNextSong() // 直接调用 playNextSong 方法
                        isTransitioningToNextSong = false
                    }
                } else {
                    handler.postDelayed(this, 1000)
                }
            }
        }
    }

    private fun playPreviousSong() {
        if (playlistSongs.isNotEmpty()) {
            val currentIndex = playlistSongs.indexOfFirst { it.id == songId }
            if (currentIndex > 0) {
                val previousSong = playlistSongs[currentIndex - 1]
                playSong(previousSong)
            } else {
                Toast.makeText(this, "已经到达列表顶部", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun playNextSong() {
        if (playlistSongs.isNotEmpty()) {
            val currentIndex = playlistSongs.indexOfFirst { it.id == songId }
            val nextIndex = if (currentIndex != -1 && currentIndex < playlistSongs.size - 1) {
                currentIndex + 1
            } else {
                0 // 回到列表的第一首歌
            }
            playSong(playlistSongs[nextIndex])
        }
    }

    private fun playRandomSong() {
        val randomIndex = (playlistSongs.indices).random()
        val randomSong = playlistSongs[randomIndex]
        Log.d("MusicPlayActivity", "Playing random song: ${randomSong.name}")
        playSong(randomSong)
        isTransitioningToNextSong = false
        Log.d("MusicPlayActivity", "isTransitioningToNextSong set to false in playRandomSong")
    }

    private fun playSong(song: Song2) {
        songId = song.id
        songName = song.name
        artistName = song.ar.joinToString(", ") { it.name }
        songImageUrl = song.al.picUrl
        currentSongUrl = null // 需要重新获取 URL

        // 停止当前播放的音乐并重置进度条
        musicService?.pauseMusic()
        handler.removeCallbacks(updateRunnable)
        rotationHandler.removeCallbacks(rotationRunnable)
        binding.seekBar.progress = 0
        binding.currentTime.text = "00:00"
        binding.totalTime.text = "00:00"

        binding.songTitle.text = songName
        binding.songArtist.text = artistName
        Glide.with(this).load(songImageUrl).into(binding.albumCover)
        getSongUrl(songId)
    }

    private fun checkPlaylistAndPauseIfEmpty() {
        if (playlistSongs.isEmpty()) {
            musicService?.pauseMusic()
            musicService?.change_isplaying()
            Log.d("MusicPlayActivity", "Playlist is empty. Music paused.")
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
        isTransitioningToNextSong = false // 重置标志位，确保不跳到下一首歌
    }

    override fun onStop() {
        super.onStop()
        if (isBound) {
            unbindService(serviceConnection)
            isBound = false
        }
        handler.removeCallbacks(updateRunnable)
        rotationHandler.removeCallbacks(rotationRunnable)
        isTransitioningToNextSong = false // 确保在停止时重置标志位
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
        isTransitioningToNextSong = false // 确保在销毁时重置标志位
    }

    private fun updatePlayOrderButton() {
        when (playOrderState) {
            0 -> binding.playorder.setImageResource(R.drawable.playorder)
            1 -> binding.playorder.setImageResource(R.drawable.cycle)
            2 -> binding.playorder.setImageResource(R.drawable.random)
        }
    }

    private fun parseLyrics(lyrics: String): List<LyricLine> {
        val lines = lyrics.split("\n")
        val lyricLines = mutableListOf<LyricLine>()

        val timeRegex = Regex("""\[(\d{2}):(\d{2})\.(\d{2,3})\]""")
        for (line in lines) {
            val matchResult = timeRegex.find(line) ?: continue
            val (minutes, seconds, milliseconds) = matchResult.destructured
            val time = minutes.toInt() * 60 * 1000 + seconds.toInt() * 1000 + milliseconds.toInt()
            val text = line.replace(timeRegex, "").trim()
            if (text.isNotEmpty()) {
                lyricLines.add(LyricLine(time, text))
            }
        }
        return lyricLines
    }

    private fun getLyrics(songId: Long) {
        val disposable = Retrofit.apiService.getlyric(songId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ lyricResponse ->
                val lyricText = lyricResponse.lrc.lyric
                lyrics = parseLyrics(lyricText)  // 这里更新歌词
            }, { error ->
                error.printStackTrace()
                Log.e("MusicPlayActivity", "Error fetching lyrics", error)
            })
        compositeDisposable.add(disposable)
    }

    private fun updateLyricsView(currentTime: Int) {
        val currentIndex = lyrics.indexOfLast { it.time <= currentTime }
        val currentLyric = lyrics.getOrNull(currentIndex)
        val nextLyric = lyrics.getOrNull(currentIndex + 1)

        if (nextLyric == null && currentLyric != null) {
            binding.lyricsView.text = currentLyric.text
            binding.lyricsView2.text = ""
        } else {
            binding.lyricsView.text = currentLyric?.text ?: ""
            binding.lyricsView2.text = nextLyric?.text ?: ""
        }
    }
}
