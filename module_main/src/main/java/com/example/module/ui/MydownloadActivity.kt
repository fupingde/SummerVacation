package com.example.module.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.module.database.MyDatabaseHelper
import com.example.module.adapters.MydownloadrvAdapter
import com.example.module.main.R
import com.example.module.main.databinding.ActivityMydownloadBinding
import com.google.android.material.appbar.AppBarLayout

class MydownloadActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMydownloadBinding
    private lateinit var databaseHelper: MyDatabaseHelper
    private lateinit var adapter: MydownloadrvAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMydownloadBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 设置 CollapsingToolbarLayout 的标题
        binding.collapsingToolbarLayout.title = "本地音乐"

        // 设置 Toolbar
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // 设置 AppBarLayout 的监听器
        binding.appBarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            val totalScrollRange = appBarLayout.totalScrollRange
            if (totalScrollRange + verticalOffset == 0) {
                // 折叠状态
                binding.collapsingToolbarLayout.title = "本地音乐"
                binding.collapsingToolbarLayout.setContentScrimResource(R.drawable.background)
            } else {
                // 展开状态
                binding.collapsingToolbarLayout.title = "本地音乐"
                binding.collapsingToolbarLayout.setContentScrim(null)
            }
        })

        databaseHelper = MyDatabaseHelper(this)
        val downloadsongs = databaseHelper.getAllDownloadedSongs()

        // 初始化适配器，传递上下文和数据
        adapter = MydownloadrvAdapter(this, downloadsongs)
        binding.songrv.layoutManager = LinearLayoutManager(this)
        binding.songrv.adapter = adapter
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
