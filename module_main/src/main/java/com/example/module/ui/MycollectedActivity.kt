package com.example.module.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.module.database.MyDatabaseHelper
import com.example.module.main.databinding.ActivityMycollectedBinding
import com.example.module.adapters.MycollectedrvAdapter
import com.example.module.main.R
import com.google.android.material.appbar.AppBarLayout

class MycollectedActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMycollectedBinding
    private lateinit var databaseHelper: MyDatabaseHelper
    private lateinit var adapter: MycollectedrvAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMycollectedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 设置 CollapsingToolbarLayout 的标题
        binding.collapsingToolbarLayout.title = "我喜欢"

        // 设置 Toolbar
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // 设置 AppBarLayout 的监听器
        binding.appBarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            val totalScrollRange = appBarLayout.totalScrollRange
            if (totalScrollRange + verticalOffset == 0) {
                // 折叠状态
                binding.collapsingToolbarLayout.title = "我喜欢"
                binding.collapsingToolbarLayout.setContentScrimResource(R.drawable.background7)
            } else {
                // 展开状态
                binding.collapsingToolbarLayout.title = "我喜欢"
                binding.collapsingToolbarLayout.setContentScrim(null)
            }
        })

        databaseHelper = MyDatabaseHelper(this)
        val collectedSongs = databaseHelper.getAllCollectedSongs()

        // 初始化适配器，传递上下文和数据
        adapter = MycollectedrvAdapter(this, collectedSongs)
        binding.songrv.layoutManager = LinearLayoutManager(this)
        binding.songrv.adapter = adapter
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
