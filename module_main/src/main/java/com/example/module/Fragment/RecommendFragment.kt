package com.example.module.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.module.main.R
import com.example.module.main.databinding.FragmentRecommendBinding
import com.example.module.ui.fragments.RecommendContentFragment
import com.example.module.ui.fragments.DynamicFragment
import com.google.android.material.tabs.TabLayoutMediator

class RecommendFragment : Fragment() {

    private var _binding: FragmentRecommendBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentRecommendBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewPager = binding.viewPager
        val tabLayout = binding.tabLayout

        val adapter = ViewPagerAdapter(requireActivity())
        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "发现"
                1 -> "动态"
                else -> null
            }
            viewPager.currentItem = 0
//            // 这里可以设置图标
//            tab.setIcon(when (position) {
//                0 -> R.drawable.recommend // 替换为你的推荐图标资源
//                1 -> R.drawable.dynamic   // 替换为你的动态图标资源
//                else -> 0
//            })
        }.attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private inner class ViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
        override fun getItemCount(): Int {
            return 2
        }

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> RecommendContentFragment() // 这是推荐页面的实际内容
                1 -> DynamicFragment() // 这是动态页面，实际内容可以在这里设置
                else -> throw IllegalStateException("Unexpected position $position")
            }
        }
    }
}
