package com.example.module.ui

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.alibaba.android.arouter.facade.annotation.Route
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.example.module.main.R
import com.example.module.main.databinding.ActivityMainBinding
import com.example.module.ui.fragments.MyFragment
import com.example.module.ui.fragments.RecommendFragment
import com.example.module.ui.services.MusicService

@Route(path = "/main/MainActivity")
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var rotationAngle = 0f
    private val rotationHandler = Handler(Looper.getMainLooper())
    private lateinit var customFab: ImageView
    private var musicService: MusicService? = null
    private var isBound = false

    private var recommendFragment: RecommendFragment? = null
    private var myFragment: MyFragment? = null

    private var songId: Long = -1L
    private var songName: String? = null
    private var songArtist: String? = null
    private var songPictureUrl: String? = null

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

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as MusicService.MusicBinder
            musicService = binder.getService()
            isBound = true
            updateUIFromService()
            musicService?.setDataChangeListener(object : MusicService.DataChangeListener {
                override fun onDataChanged(songId: Long, songName: String?, songArtist: String?, songPictureUrl: String?) {
                    updateUI(songId, songName, songArtist, songPictureUrl)
                }
            })
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            isBound = false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 绑定 MusicService
        Intent(this, MusicService::class.java).also { intent ->
            bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
        }

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 初始化Fragment
        recommendFragment = RecommendFragment()
        myFragment = MyFragment()

        supportFragmentManager.beginTransaction().apply {
            add(R.id.fragment_container, recommendFragment!!)
            add(R.id.fragment_container, myFragment!!)
            hide(myFragment!!)
        }.commit()

        binding.bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_recommend -> {
                    supportFragmentManager.beginTransaction()
                        .hide(myFragment!!)
                        .show(recommendFragment!!)
                        .commit()
                    true
                }
                R.id.navigation_my -> {
                    supportFragmentManager.beginTransaction()
                        .hide(recommendFragment!!)
                        .show(myFragment!!)
                        .commit()
                    true
                }
                R.id.navigation_dynamic -> false
                else -> false
            }
        }

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .show(recommendFragment!!)
                .commit()
        }

        setupCustomFab()
    }

    private fun setupCustomFab() {
        customFab = findViewById(R.id.custom_fab)
        val requestOptions = RequestOptions().circleCrop()

        customFab.setOnClickListener {
            if (songId != -1L) {
                val intent = Intent(this, MusicPlayActivity::class.java).apply {
                    putExtra("SONG_ID", songId)
                    putExtra("SONG_NAME", songName)
                    putExtra("SONG_ARTIST", songArtist)
                    putExtra("SONG_PICTUREURL", songPictureUrl)
                }
                startActivity(intent)
            } else {
                Toast.makeText(this, "No song is currently playing", Toast.LENGTH_SHORT).show()
            }
        }

        rotationHandler.post(rotationRunnable)
    }

    private fun updateUIFromService() {
        musicService?.let { service ->
            songId = service.songId
            songName = service.songName
            songArtist = service.songArtist
            songPictureUrl = service.songPictureUrl

            Log.d("MainActivity", "updateUIFromService: songId=$songId, songName=$songName, songArtist=$songArtist, songPictureUrl=$songPictureUrl")

            updateUI(songId, songName, songArtist, songPictureUrl)
        }
    }

    private fun updateUI(songId: Long, songName: String?, songArtist: String?, songPictureUrl: String?) {
        this.songId = songId
        this.songName = songName
        this.songArtist = songArtist
        this.songPictureUrl = songPictureUrl

        Glide.with(this)
            .load(songPictureUrl)
            .apply(RequestOptions.circleCropTransform())
            .into(customFab)
    }

    override fun onDestroy() {
        super.onDestroy()
        rotationHandler.removeCallbacks(rotationRunnable)
        if (isBound) {
            unbindService(serviceConnection)
            isBound = false
        }
    }
}

