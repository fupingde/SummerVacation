package com.example.module.broadcast.ui

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModelProvider
import com.example.module.broadcast.R
import com.example.module.broadcast.ViewModel.MvViewModel
import com.example.module.broadcast.databinding.ActivityBroadcastBinding
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ui.PlayerView

class BroadcastActivity : ComponentActivity() {
    val songViewModel by lazy {
        ViewModelProvider(this)[MvViewModel::class.java]
    }

    private val mbinding: ActivityBroadcastBinding by lazy {
        ActivityBroadcastBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mbinding.root)
     initView()
    }

    private fun initView() {
        mbinding
    }
}



