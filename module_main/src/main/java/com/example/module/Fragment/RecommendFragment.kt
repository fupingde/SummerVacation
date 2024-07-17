package com.example.module.ui.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.module.main.databinding.FragmentRecommendBinding
import com.example.module.ui.adapters.BannerAdapter
import com.example.module.ui.adapters.Mainrv1Adapter
import com.example.module.ui.viewmodel.RecommendViewModel

class RecommendFragment : Fragment() {

    private var _binding: FragmentRecommendBinding? = null
    private val binding get() = _binding!!

    private lateinit var recommendViewModel: RecommendViewModel

    private val handler = Handler(Looper.getMainLooper())
    private val autoScrollRunnable = object : Runnable {
        override fun run() {
            val itemCount = binding.mainrv1.adapter?.itemCount ?: 0
            if (itemCount > 0) {
                val nextItem = (binding.mainrv1.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition() + 1
                if (nextItem < itemCount) {
                    binding.mainrv1.smoothScrollToPosition(nextItem)
                } else {
                    binding.mainrv1.smoothScrollToPosition(0)
                }
            }
            handler.postDelayed(this, 3000)
        }
    }

    private val bannerAutoScrollRunnable = object : Runnable {
        override fun run() {
            val itemCount = binding.bannerViewPager.adapter?.itemCount ?: 0
            if (itemCount > 0) {
                val nextItem = binding.bannerViewPager.currentItem + 1
                binding.bannerViewPager.currentItem = if (nextItem < itemCount) nextItem else 0
            }
            handler.postDelayed(this, 3000)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentRecommendBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 手动初始化 ViewModel
        recommendViewModel = ViewModelProvider(this).get(RecommendViewModel::class.java)

        setupRecyclerView()

        recommendViewModel.tuijianGedan.observe(viewLifecycleOwner, { data ->
            val adapter = Mainrv1Adapter(data)
            binding.mainrv1.adapter = adapter

            handler.post(autoScrollRunnable)
        })

        recommendViewModel.banner.observe(viewLifecycleOwner, { bannerData ->
            val bannerAdapter = BannerAdapter(bannerData.data)
            binding.bannerViewPager.adapter = bannerAdapter

            handler.post(bannerAutoScrollRunnable)
        })

        recommendViewModel.fetchTuijianGedan()
        recommendViewModel.fetchBanner()
    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.mainrv1.layoutManager = layoutManager
    }

    override fun onDestroyView() {
        super.onDestroyView()
        handler.removeCallbacks(autoScrollRunnable)
        handler.removeCallbacks(bannerAutoScrollRunnable)
        _binding = null
    }
}
