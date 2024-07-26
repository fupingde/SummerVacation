package com.example.module.ui.fragments

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class MyFragmentAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> MusicFragment()
            1 -> StoryFragment()
            2 -> ChannelFragment()
            else -> throw IllegalStateException("Unexpected position: $position")
        }
    }
}
