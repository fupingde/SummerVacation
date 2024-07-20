package com.example.module.search.ui

import SongViewModel
import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.module.search.adpter.VpAdapter
import com.example.module.search.databinding.ActivitySearchBinding
import com.example.module.search.fragment.MvFragment
import com.example.module.search.fragment.SinglistFragment
import com.example.module.search.fragment.SongFragment
import com.example.module.search.network.FragmentInterface
import com.example.module.search.viewmodel.AlbumViewModel
import com.example.module.search.viewmodel.SingerViewModel
import com.google.android.material.tabs.TabLayoutMediator

class SearchActivity : AppCompatActivity() {
    val mbinding by lazy {
        ActivitySearchBinding.inflate(layoutInflater)
    }
    private val fragmentLsit = ArrayList<FragmentInterface>()
    val songviewModel by lazy { ViewModelProvider(this)[SongViewModel::class.java] }
    val albumViewModel by lazy { ViewModelProvider(this)[AlbumViewModel::class.java] }
    val singerViewModel by lazy { ViewModelProvider(this)[SingerViewModel::class.java] }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(mbinding.root)
        initView()
        initClick()
    }

    private fun initClick() {
        mbinding.tvSearch.setOnClickListener {
            songviewModel.getSongInfo(mbinding.searchView.query.toString())
            albumViewModel.getSongInfo(mbinding.searchView.query.toString())
            singerViewModel.getSongInfo(mbinding.searchView.query.toString())
            Log.d("fas", mbinding.searchView.query.toString())

        }

    }

    private fun initView() {
        //隐藏状态栏
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        fragmentLsit.let {
            it.add(object : FragmentInterface {
                override fun back(): Fragment {
                    return SongFragment()
                }

            })
            it.add(object : FragmentInterface {
                override fun back(): Fragment {
                    return SinglistFragment()
                }

            })
            it.add(object : FragmentInterface {
                override fun back(): Fragment {
                    return MvFragment()
                }

            })
        }



        mbinding.viewPager2.adapter = VpAdapter(this, fragmentLsit)
        TabLayoutMediator(mbinding.tabLayout, mbinding.viewPager2) { tab, position ->
            tab.text = when (position) {
                0 -> "单曲"
                1 -> "专辑"
                else -> "歌手"
            }
        }.attach()
        mbinding.tabLayout.getTabAt(2)?.select()
        mbinding.searchView.setOnQueryTextFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                imm.showSoftInput(v, InputMethodManager.SHOW_IMPLICIT)
            }
        }
    }
}
