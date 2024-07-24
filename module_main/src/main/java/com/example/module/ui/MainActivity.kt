package com.example.module.ui

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.example.module.main.R
import com.example.module.main.databinding.ActivityMainBinding
import com.example.module.ui.fragments.MyFragment
import com.example.module.ui.fragments.RecommendFragment
import com.example.module.ui.viewmodel.SongViewModel
import com.example.module.ui.viewmodel.ViewModelSingleton

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var songViewModel: SongViewModel
    private var rotationAngle = 0f
    private val rotationHandler = Handler(Looper.getMainLooper())
    private lateinit var customFab: ImageView

    private val rotationRunnable: Runnable = object : Runnable {
        override fun run() {
            customFab.rotation = rotationAngle
            rotationAngle += 2f
            if (rotationAngle >= 360f) {
                rotationAngle = 0f
            }
            rotationHandler.postDelayed(this, 50)
        }
    }

    private var songId: Long = -1L
    private var songName: String? = null
    private var songArtist: String? = null
    private var songPictureUrl: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        songViewModel = ViewModelSingleton.getSongViewModel(application)

        // 接收传递过来的歌曲信息
        intent?.let {
            songId = it.getLongExtra("SONG_ID", -1L)
            songName = it.getStringExtra("SONG_NAME")
            songArtist = it.getStringExtra("SONG_ARTIST")
            songPictureUrl = it.getStringExtra("SONG_PICTUREURL")
        }

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_recommend -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, RecommendFragment())
                        .commit()
                    true
                }
                R.id.navigation_my -> { // 移除 navigation_dynamic
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, MyFragment())
                        .commit()
                    true
                }
                R.id.navigation_dynamic -> false // 取消 navigation_dynamic 的点击事件
                else -> false
            }
        }

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, RecommendFragment())
                .commit()
        }

        setupCustomFab()
        observeSongData()
    }

    private fun setupCustomFab() {
        customFab = findViewById(R.id.custom_fab)
        Log.d("MainActivity", "customFab initialized")

        val requestOptions = RequestOptions()
            .circleCrop() // 圆形裁剪

        songViewModel.songPictureUrl.observe(this, Observer { url ->
            Log.d("MainActivity", "Observer triggered with URL: $url")
            url?.let {
                Log.d("MainActivity", "Loading image URL: $url")

                // 在 customFab 上加载图片
                Glide.with(this)
                    .load(url)
                    .apply(requestOptions)
                    .listener(object : RequestListener<Drawable> {
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Drawable>?,
                            isFirstResource: Boolean
                        ): Boolean {
                            Log.e("MainActivity", "Error loading image: $e")
                            return false
                        }

                        override fun onResourceReady(
                            resource: Drawable?,
                            model: Any?,
                            target: Target<Drawable>?,
                            dataSource: com.bumptech.glide.load.DataSource?,
                            isFirstResource: Boolean
                        ): Boolean {
                            Log.d("MainActivity", "Image loaded successfully")
                            customFab.setImageDrawable(resource) // 设置裁剪后的圆形图片作为按钮的图像
                            return true
                        }
                    })
                    .into(customFab)
            }
        })

        // 为 customFab 添加点击事件
        customFab.setOnClickListener {
            val intent = Intent(this, MusicPlayActivity::class.java).apply {
                putExtra("SONG_ID", songId)
                putExtra("SONG_NAME", songName)
                putExtra("SONG_ARTIST", songArtist)
                putExtra("SONG_PICTUREURL", songPictureUrl)
            }
            startActivity(intent)
        }

        rotationHandler.post(rotationRunnable)
    }

    private fun observeSongData() {
        songViewModel.songId.observe(this, Observer { id ->
            Log.d("MainActivity", "Song ID updated: $id")
            songId = id.toLong()
        })
        songViewModel.songName.observe(this, Observer { name ->
            Log.d("MainActivity", "Song Name updated: $name")
            songName = name
        })
        songViewModel.songArtist.observe(this, Observer { artist ->
            Log.d("MainActivity", "Song Artist updated: $artist")
            songArtist = artist
        })
        songViewModel.songPictureUrl.observe(this, Observer { url ->
            Log.d("MainActivity", "Song Picture URL updated: $url")
            songPictureUrl = url
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        rotationHandler.removeCallbacks(rotationRunnable)
    }
}
