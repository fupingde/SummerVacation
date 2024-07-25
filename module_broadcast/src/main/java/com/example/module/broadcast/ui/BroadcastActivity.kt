package com.example.module.broadcast.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.MenuInflater
import android.view.MenuItem
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.example.module.broadcast.R
import com.example.module.broadcast.ViewModel.MvViewModel
import com.example.module.broadcast.ViewModel.MvdataViewModel
import com.example.module.broadcast.ViewModel.OtherViewModel
import com.example.module.broadcast.adapter.MvPagerAdapte
import com.example.module.broadcast.databinding.ActivityBroadcastBinding
import com.example.module.broadcast.fragment.MvFragment
import com.example.module.broadcast.fragment.OtherFragment

@Route(path = "/broadcast/BroadcastActivity")

class BroadcastActivity : AppCompatActivity() {
    @Autowired(name = "mvid")
    @JvmField var mvid:Long=0
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
        ARouter.getInstance().inject(this)
        initView()
    }


    @SuppressLint("SuspiciousIndentation")
    private fun initView() {
        mbinding.buttonBack.setOnClickListener {
            finish()
        }
            mbinding.buttonDetail.setOnClickListener {view->
                val popupMenu = PopupMenu(this, view)
                val inflater: MenuInflater = popupMenu.menuInflater
                inflater.inflate(R.menu.popup_menu, popupMenu.menu)
                popupMenu.setOnMenuItemClickListener { item: MenuItem ->
                    when (item.itemId) {
                        R.id.option1 -> {
                            // 处理选项1点击

                            true
                        }
                        R.id.option2 -> {
                            // 处理选项2点击
                            true
                        }

                        else -> false
                    }
                }

                // 显示 PopupMenu
                popupMenu.show()
            }



        //隐藏状态栏
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        mbinding.viewPager.adapter = mvPagerAdapter
        Log.d("mvids",mvid.toString())
        mvViewModel.getSongInfo(mvid)
        mvdataViewModel.getMvdata(mvid)
        otheridViewModel.getSongInfo(mvid)
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
            finish()
//            ARouter.getInstance().build("/search/SearchActivity").navigation()
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



