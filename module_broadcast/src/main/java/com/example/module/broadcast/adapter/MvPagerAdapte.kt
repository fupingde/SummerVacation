package com.example.module.broadcast.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class MvPagerAdapte(activity: FragmentActivity) : FragmentStateAdapter(activity) {

    private val fragments = mutableListOf<Fragment>()

    // 返回 Fragment 的数量
    override fun getItemCount(): Int {
        return fragments.size
    }

    // 创建每个 Fragment 的实例
    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }

    // 更新 Fragment 列表并通知适配器数据已更改
    fun updateaddFragments(newFragments: List<Fragment>) {
        fragments.addAll(newFragments)
     }

    fun updateFragemt(){
        notifyDataSetChanged()
    }
}