package com.example.module.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.alibaba.android.arouter.launcher.ARouter
import com.example.module.main.databinding.FragmentRecommendBinding
import com.google.android.material.tabs.TabLayoutMediator

class RecommendFragment : Fragment() {

    private var _binding: FragmentRecommendBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRecommendBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.searchView.setOnClickListener {
            ARouter.getInstance().build("/search/SearchActivity")
                .navigation()
        }
        val viewPager = binding.viewPager
        val tabLayout = binding.tabLayout

        val adapter = ViewPagerAdapter(requireActivity())
        viewPager.adapter = adapter
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "推荐"
                1 -> "发现"
                else -> null
            }
            viewPager.currentItem = 1 // 设置初始页面为“发现”
        }.attach()

        // 确保 ViewPager2 的第一个页面是“发现”
        viewPager.setCurrentItem(1, false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private inner class ViewPagerAdapter(fragmentActivity: FragmentActivity) :
        FragmentStateAdapter(fragmentActivity) {
        override fun getItemCount(): Int {
            return 2
        }

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> DynamicFragment() // 动态页面
                1 -> RecommendContentFragment() // 发现页面
                else -> throw IllegalStateException("Unexpected position $position")
            }
        }
    }
}
