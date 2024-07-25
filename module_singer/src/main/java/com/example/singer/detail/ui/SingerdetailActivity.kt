package com.example.singer.detail.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.bumptech.glide.Glide
import com.example.singer.detail.R
import com.example.singer.detail.databinding.ActivitySingerdetailBinding
import com.example.singer.detail.ui.Adapter.VpAdapter
import com.example.singer.detail.ui.ViewModel.SongViewModel
import com.example.singer.detail.ui.fragment.DetailFragment
import com.example.singer.detail.ui.fragment.SongFragment
import com.google.android.material.tabs.TabLayoutMediator

@Route(path = "/singer/SingerdetailActivity")
class SingerdetailActivity : AppCompatActivity() {
    @Autowired(name = "singerid")
    @JvmField
    var singerid: Long = 0
    val mbinding by lazy { ActivitySingerdetailBinding.inflate(layoutInflater) }
    val viewModel by lazy { ViewModelProvider(this)[SongViewModel::class.java] }
    private val fragmentLsit = ArrayList<FragmentInterface>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(mbinding.root)
        ARouter.getInstance().inject(this)
        viewModel.getSongIn(singerid)
        initView()

    }

    private fun initView() {
        setSupportActionBar(mbinding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = ""
        mbinding.toolbar.setNavigationOnClickListener {
            // 返回按钮点击事件，结束当前活动
            finish()
        }
        viewModel.imageData.observe(this, Observer { image ->
            image?.let {
                Glide.with(this).load(it[0].picUrl)
                    .into(mbinding.artistImage)
            }
        })
        fragmentLsit.let {

            it.add(object : FragmentInterface {
                override fun back(): Fragment {
                    return DetailFragment()
                }
            })
            it.add(object : FragmentInterface {
                override fun back(): Fragment {
                    return SongFragment()
                }
            })
        }

        mbinding.viewPager.adapter = VpAdapter(this, fragmentLsit)
        TabLayoutMediator(mbinding.tabLayout, mbinding.viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "主页"
                else -> "歌曲"

            }

        }.attach()
    }
}