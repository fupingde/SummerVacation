package com.example.singer.detail.ui.Adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.singer.detail.ui.FragmentInterface

class VpAdapter(fragmentActivity: FragmentActivity,private val fragments:ArrayList<FragmentInterface> ) : FragmentStateAdapter(fragmentActivity) {


    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
       return fragments.get(position).back()


    }
}