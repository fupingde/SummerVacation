package com.example.module.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.Network.api.FragmentInterfacer

class MyVp2Adapter(fragmentActivity: FragmentActivity, private val fragments:ArrayList<FragmentInterfacer> ) : FragmentStateAdapter(fragmentActivity) {


    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments.get(position).back()


    }
}