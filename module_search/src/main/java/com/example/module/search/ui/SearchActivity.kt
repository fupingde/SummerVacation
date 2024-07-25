package com.example.module.search.ui

import SongViewModel
import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.example.module.search.adpter.VpAdapter
import com.example.module.search.databinding.ActivitySearchBinding
import com.example.module.search.fragment.MvFragment
import com.example.module.search.fragment.SingerFragment
import com.example.module.search.fragment.SinglistFragment
import com.example.module.search.fragment.SongFragment
import com.example.module.search.network.FragmentInterface
import com.example.module.search.viewmodel.AlbumViewModel
import com.example.module.search.viewmodel.MvViewModel
import com.example.module.search.viewmodel.SharedViewModel
import com.example.module.search.viewmodel.SingerViewModel
import com.google.android.material.tabs.TabLayoutMediator

@Route(path = "/search/SearchActivity")
class SearchActivity : AppCompatActivity() {
    val mbinding by lazy {
        ActivitySearchBinding.inflate(layoutInflater)
    }
    private val fragmentLsit = ArrayList<FragmentInterface>()
    val songviewModel by lazy { ViewModelProvider(this)[SongViewModel::class.java] }
    val albumViewModel by lazy { ViewModelProvider(this)[AlbumViewModel::class.java] }
    val singerViewModel by lazy { ViewModelProvider(this)[SingerViewModel::class.java] }
    val mvViewModel by lazy { ViewModelProvider(this)[MvViewModel::class.java] }
    val sharedViewModel by lazy { ViewModelProvider(this)[SharedViewModel::class.java] }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(mbinding.root)
        ARouter.getInstance().inject(this)

        initView()
        initClick()
    }

    private fun initClick() {
        mbinding.tvSearch.setOnClickListener {
            if (mbinding.searchView.query.toString()!=null){
            songviewModel.getSongInfo(mbinding.searchView.query.toString())
            albumViewModel.getSongInfo(mbinding.searchView.query.toString())
            singerViewModel.getSongInfo(mbinding.searchView.query.toString())
            mvViewModel.getMvidata(mbinding.searchView.query.toString())
            Log.d("edixView", mbinding.searchView.query.toString())}
        }
        mbinding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    sharedViewModel.searchQuery.value = it
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    sharedViewModel.searchQuery.value = it
                }
                return true
            }
        })
        mbinding.imBack.setOnClickListener {
            finish()
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
            it.add(object : FragmentInterface {
                override fun back(): Fragment {
                    return SingerFragment()
                }
            })
        }



        mbinding.viewPager2.adapter = VpAdapter(this, fragmentLsit)
        TabLayoutMediator(mbinding.tabLayout, mbinding.viewPager2) { tab, position ->
            tab.text = when (position) {
                0 -> "单曲"
                1 -> "专辑"
                2 -> "歌手"
                else -> "视频"
            }
        }.attach()
        mbinding.searchView.setOnQueryTextFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                //显示软键盘方便使用
                val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                imm.showSoftInput(v, InputMethodManager.SHOW_IMPLICIT)
            }
        }
    }
}
