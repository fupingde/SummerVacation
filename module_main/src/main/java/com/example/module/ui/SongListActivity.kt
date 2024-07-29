package com.example.module.ui.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.network.Bean.Song2
import com.example.module.database.MyDatabaseHelper
import com.example.module.main.R
import com.example.module.main.databinding.ActivitySongListBinding
import com.example.module.ui.MusicPlayActivity
import com.example.module.ui.adapters.SongsAdapter
import com.example.module.ui.viewmodel.SongListViewModel
import com.example.module.ui.viewmodel.SongViewModel
import com.example.network.SingletionClass.ViewModelSingleton
import com.google.android.material.appbar.AppBarLayout

class SongListActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySongListBinding
    private val viewModel: SongListViewModel by viewModels()
    private lateinit var songViewModel: SongViewModel
    private lateinit var dbHelper: MyDatabaseHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 初始化数据库助手
        dbHelper = MyDatabaseHelper(this)

        // 使用单例类获取SongViewModel实例
        songViewModel = ViewModelSingleton.getSongViewModel(application)

        val playlistId = intent.getLongExtra("playlistId", -1L)
        Log.d("SongListActivity", "Received playlistId: $playlistId")
        val playlistName = intent.getStringExtra("playlistName") ?: ""
        Log.d("SongListActivity", "Received playlistName: $playlistName")
        val playlistImageUrl = intent.getStringExtra("playlistImageUrl") ?: ""
        Log.d("SongListActivity", "Received playlistImageUrl: $playlistImageUrl")

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""

        binding.toolbar.setNavigationOnClickListener {
            finish()
        }

        Glide.with(this)
            .load(playlistImageUrl)
            .into(binding.playlistImage)

        Glide.with(this)
            .load(playlistImageUrl)
            .into(binding.playlistThumbnail)

        binding.playlistName.text = playlistName

        binding.songrv.layoutManager = LinearLayoutManager(this)

        viewModel.songs.observe(this, Observer { songs ->
            val adapter = SongsAdapter(songs.songs, this::onSongItemClick)
            binding.songrv.adapter = adapter
        })

        viewModel.playlistName.observe(this, Observer { name ->
            binding.playlistName.text = name
        })

        viewModel.setPlaylistData(playlistId, playlistName)

        binding.appBarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            val scrollRange = appBarLayout.totalScrollRange
            if (scrollRange + verticalOffset == 0) {
                binding.toolbar.title = playlistName
                binding.toolbar.setBackgroundResource(R.drawable.background)
                binding.marqueeTextView.text = playlistName
                binding.marqueeTextView.isSelected = true
            } else {
                binding.toolbar.title = ""
                binding.toolbar.setBackgroundColor(ContextCompat.getColor(this, android.R.color.transparent))
                binding.marqueeTextView.text = ""
                binding.marqueeTextView.isSelected = false
            }
        })

        binding.collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar)

        // 在设置 collectbutton 点击监听器之前检查数据库中是否有相关数据
        val isCollected = dbHelper.isPlaylistCollected(playlistId)

        if (isCollected) {

            binding.collectbutton.setImageResource(R.drawable.collected)
        } else {
            binding.collectbutton.setImageResource(R.drawable.shoucang)
        }

        binding.collectbutton.setOnClickListener {
            // 获取播放列表数据
            val playlistId = intent.getLongExtra("playlistId", -1L)
            val playlistName = intent.getStringExtra("playlistName") ?: ""
            val playlistImageUrl = intent.getStringExtra("playlistImageUrl") ?: ""

            // 检查数据库中是否有相关数据
            val isCollected = dbHelper.isPlaylistCollected(playlistId)

            if (isCollected) {
                // 如果数据已存在，删除数据并更换按钮图片
                dbHelper.deleteCollectedPlaylist(playlistId)
                binding.collectbutton.setImageResource(R.drawable.shoucang)
                Toast.makeText(this, "取消收藏", Toast.LENGTH_SHORT).show()
            } else {
                // 如果数据不存在，添加数据并更换按钮图片
                dbHelper.insertCollectedPlaylist(playlistId, playlistName, playlistImageUrl)
                binding.collectbutton.setImageResource(R.drawable.collected)
                Toast.makeText(this, "收藏成功", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun onSongItemClick(song: Song2, songs: List<Song2>, position: Int) {
        val intent = Intent(this, MusicPlayActivity::class.java).apply {
            putExtra("SONG_ID", song.id)
            putExtra("SONG_NAME", song.name)
            putExtra("SONG_ARTIST", song.ar.firstOrNull()?.name)
            putExtra("SONG_PICTUREURL", song.al.picUrl)
            putExtra("PLAYLIST_SONGS", ArrayList(songs)) // 确保传递的数据是 ArrayList<Song2>
            Log.d("musicplayactivity", "PLAYLIST_SONGS:${ArrayList(songs)} ")
        }
        startActivity(intent)
    }


}
