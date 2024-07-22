package com.example.module.search.adpter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.module.search.fragment.MvFragment
import com.example.module.search.fragment.SinglistFragment
import com.example.module.search.fragment.SongFragment
import com.example.module.search.network.FragmentInterface

class VpAdapter(fragmentActivity: FragmentActivity,private val fragments:ArrayList<FragmentInterface> ) : FragmentStateAdapter(fragmentActivity) {


    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
       return fragments.get(position).back()


    }
}