package com.example.module.ui

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.example.module.main.R
import com.example.module.main.databinding.ActivityMainBinding
import com.example.module.ui.fragments.DynamicFragment
import com.example.module.ui.fragments.MyFragment
import com.example.module.ui.fragments.RecommendFragment
import com.example.module.ui.viewmodel.SongViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var songViewModel: SongViewModel
    private var rotationAngle = 0f
    private val rotationHandler = Handler(Looper.getMainLooper())
    private lateinit var dynamicFab: FloatingActionButton

    private val rotationRunnable: Runnable = object : Runnable {
        override fun run() {
            dynamicFab.rotation = rotationAngle
            rotationAngle += 2f
            if (rotationAngle >= 360f) {
                rotationAngle = 0f
            }
            rotationHandler.postDelayed(this, 50)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        songViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(SongViewModel::class.java)

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
                R.id.navigation_dynamic -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, DynamicFragment())
                        .commit()
                    true
                }
                R.id.navigation_my -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, MyFragment())
                        .commit()
                    true
                }
                else -> false
            }
        }

        // 加载默认的 Fragment
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, RecommendFragment())
                .commit()
        }

        setupDynamicFab()

        // 观察歌曲数据变化
        observeSongData()
    }

    private fun setupDynamicFab() {
        dynamicFab = findViewById(R.id.dynamic_fab)

        // 初始加载传递的图片
        songViewModel.songPictureUrl.observe(this, Observer { url ->
            url?.let {
                Log.d("MainActivity", "Loading image URL: $url")
                Glide.with(this)
                    .load(url)
                    .apply(RequestOptions().circleCrop())
                    .listener(object : RequestListener<Drawable> {
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Drawable>?,
                            isFirstResource: Boolean
                        ): Boolean {
                            Log.e("MainActivity", "Error loading image: $e")
                            Toast.makeText(this@MainActivity, "Failed to load image", Toast.LENGTH_SHORT).show()
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
                            return false
                        }
                    })
                    .into(dynamicFab)
            }
        })

        // 开始旋转动画
        rotationHandler.post(rotationRunnable)
    }

    private fun observeSongData() {
        songViewModel.songId.observe(this, Observer { id ->
            Log.d("MainActivity", "Song ID updated: $id")
        })
        songViewModel.songName.observe(this, Observer { name ->
            Log.d("MainActivity", "Song Name updated: $name")
        })
        songViewModel.songArtist.observe(this, Observer { artist ->
            Log.d("MainActivity", "Song Artist updated: $artist")
        })
        songViewModel.songPictureUrl.observe(this, Observer { url ->
            Log.d("MainActivity", "Song Picture URL updated: $url")
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        rotationHandler.removeCallbacks(rotationRunnable)
    }
}
