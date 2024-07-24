package com.example.module.albums.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.bumptech.glide.Glide
import com.example.module.albums.adapter.RvAdapter
import com.example.module.albums.databinding.ActivityAlbumBinding
import com.example.module.albums.viewmodel.AlbumsViewModel

@Route(path = "/albums/AlbumActivity")
class AlbumActivity : AppCompatActivity() {
    @Autowired(name = "id")
    @JvmField var id: Long = 0
    val mbinding by lazy { ActivityAlbumBinding.inflate(layoutInflater) }
    val viewModel by lazy { ViewModelProvider(this)[AlbumsViewModel::class.java] }
    val adapter by lazy { RvAdapter() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(mbinding.root)
        ARouter.getInstance().inject(this)
        viewModel.getSongIn(id)
        initView()
    }

    private fun initView() {
        viewModel.imageData.observe(this, Observer { url ->
            url?.let {
                //   Log.d("AlbumImage",it[0])
                Glide.with(this).load(it[0].picUrl).into(mbinding.headerImage)
                mbinding.describe.text = it[0].description
                mbinding.name.text = it[0].name
            }

        })
        viewModel.songData.observe(this, Observer { data ->
            data?.let {
                adapter.submitList(it)
            }

        })
        mbinding.recyclerView.adapter = adapter
        mbinding.recyclerView.layoutManager = LinearLayoutManager(this)

    }
}