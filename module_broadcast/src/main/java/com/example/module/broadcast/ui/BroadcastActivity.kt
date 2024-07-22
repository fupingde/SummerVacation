package com.example.module.broadcast.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.module.broadcast.ViewModel.MvViewModel
import com.example.module.broadcast.ViewModel.MvdataViewModel
import com.example.module.broadcast.ViewModel.OtherViewModel
import com.example.module.broadcast.adapter.MvPagerAdapte
import com.example.module.broadcast.databinding.ActivityBroadcastBinding
import com.example.module.broadcast.fragment.MvFragment
import com.example.module.broadcast.fragment.OtherFragment

class BroadcastActivity : AppCompatActivity() {
    val mvViewModel by lazy {
        ViewModelProvider(this)[MvViewModel::class.java]
    }
    val mvdataViewModel by lazy {
        ViewModelProvider(this)[MvdataViewModel::class.java]
    }
    val otheridViewModel by lazy {
        ViewModelProvider(this)[OtherViewModel::class.java]
    }
    private val mbinding: ActivityBroadcastBinding by lazy {
        ActivityBroadcastBinding.inflate(layoutInflater)
    }
    private val mvPagerAdapter by lazy { MvPagerAdapte(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mbinding.root)

        initView()
    }


    @SuppressLint("SuspiciousIndentation")
    private fun initView() {
        //隐藏状态栏
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        mbinding.viewPager.adapter = mvPagerAdapter
        mvViewModel.getSongInfo(10896407)
        mvdataViewModel.getMvdata(10896407)
        otheridViewModel.getSongInfo(10896407)
        addFragments()
        otheridViewModel.OtehrMvid.observe(this, Observer { id ->
            id?.let {
                var order = 0
                for (mv in it) {
                    Log.d("fas", "传入id+" + mv.id+","+order)
                    val otherFragment = listOf(OtherFragment.newInstance(mv.id, order))
                    mvPagerAdapter.updateaddFragments(otherFragment)
                    order += 1
                }
                mvPagerAdapter.updateFragemt()

            }


        })
        mbinding.buttonBack.setOnClickListener {

        }


    }

    private fun addFragments() {
        val newFragments = listOf(
            MvFragment.newInstance()
        )
        mvPagerAdapter.updateaddFragments(newFragments)
        mvPagerAdapter.updateFragemt()
    }

}



