package com.example.module.ui.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.Network.Bean.Song2
import com.example.module.main.R
import com.example.module.main.databinding.ActivitySongListBinding
import com.example.module.ui.adapters.SongsAdapter
import com.example.module.ui.viewmodel.SongListViewModel
import com.google.android.material.appbar.AppBarLayout

class SongListActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySongListBinding
    private val viewModel: SongListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val playlistId = intent.getLongExtra("playlistId", -1L)
        Log.d("SongListActivity", "Received playlistId: $playlistId")
        val playlistName = intent.getStringExtra("playlistName") ?: ""
        Log.d("SongListActivity", "Received playlistName: $playlistName")
        val playlistImageUrl = intent.getStringExtra("playlistImageUrl") ?: ""
        Log.d("SongListActivity", "Received playlistImageUrl: $playlistImageUrl")

        // 设置Toolbar
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""

        binding.toolbar.setNavigationOnClickListener {
            finish()
        }

        // 设置顶部背景图片
        Glide.with(this)
            .load(playlistImageUrl)
            .into(binding.playlistImage)

        // 设置缩略图和名称
        Glide.with(this)
            .load(playlistImageUrl)
            .into(binding.playlistThumbnail)

        binding.playlistName.text = playlistName

        // 设置RecyclerView
        binding.songrv.layoutManager = LinearLayoutManager(this)

        // 观察歌曲数据变化并更新UI
        viewModel.songs.observe(this, Observer { songs ->
            val adapter = SongsAdapter(songs.songs, this::onSongItemClick)
            binding.songrv.adapter = adapter
        })

        // 设置歌单名称
        viewModel.playlistName.observe(this, Observer { name ->
            binding.playlistName.text = name
        })

        // 设置传入的数据
        viewModel.setPlaylistData(playlistId, playlistName)

        // 动态调整Toolbar标题和背景
        binding.appBarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            val scrollRange = appBarLayout.totalScrollRange
            if (scrollRange + verticalOffset == 0) {
                binding.toolbar.title = playlistName
                binding.toolbar.setBackgroundResource(R.drawable.background) // 设置背景图片
                binding.marqueeTextView.text = playlistName
                binding.marqueeTextView.isSelected = true // 启动跑马灯效果
            } else {
                binding.toolbar.title = ""
                binding.toolbar.setBackgroundColor(ContextCompat.getColor(this, android.R.color.transparent)) // 确保背景透明
                binding.marqueeTextView.text = ""
                binding.marqueeTextView.isSelected = false // 关闭跑马灯效果
            }
        })

        // 设置CollapsingToolbarLayout的折叠后样式
        binding.collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar)
    }

    private fun onSongItemClick(song: Song2, songs: List<Song2>, position: Int) {
        val intent = Intent(this, MusicPlayActivity::class.java).apply {
            putExtra("SONG_ID", song.id)
            putExtra("SONG_NAME", song.name)
            putExtra("SONG_ARTIST", song.ar.find { it.equals("name") })
            putExtra("SONG_PICTUREURL",song.al.picUrl)
        }
        startActivity(intent)
    }
}
