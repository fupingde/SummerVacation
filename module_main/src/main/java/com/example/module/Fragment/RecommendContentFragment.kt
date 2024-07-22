package com.example.module.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.module.main.databinding.FragmentRecommendContentBinding
import com.example.module.ui.activities.SongListActivity
import com.example.module.ui.adapters.BannerAdapter
import com.example.module.ui.adapters.Mainrv1Adapter
import com.example.module.ui.adapters.Mainrv2Adapter
import com.example.module.ui.adapters.Mainvp4Adapter
import com.example.module.ui.viewmodel.RecommendViewModel
import DepthAndZoomPageTransformer

class RecommendContentFragment : Fragment() {

    private var _binding: FragmentRecommendContentBinding? = null
    private val binding get() = _binding!!

    private lateinit var recommendViewModel: RecommendViewModel

    private val handler = Handler(Looper.getMainLooper())

    private val bannerAutoScrollRunnable = object : Runnable {
        override fun run() {
            val itemCount = binding.bannerViewPager.adapter?.itemCount ?: 0
            if (itemCount > 0) {
                val nextItem = binding.bannerViewPager.currentItem + 1
                binding.bannerViewPager.currentItem = if (nextItem < itemCount) nextItem else 0
            }
            handler.postDelayed(this, 6000)
        }
    }

    private val mainvpAutoScrollRunnable = object : Runnable {
        override fun run() {
            val itemCount = binding.mainvp.adapter?.itemCount ?: 0
            if (itemCount > 0) {
                val nextItem = binding.mainvp.currentItem + 1
                binding.mainvp.currentItem = if (nextItem < itemCount) nextItem else 0
            }
            handler.postDelayed(this, 6000)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentRecommendContentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recommendViewModel = ViewModelProvider(this).get(RecommendViewModel::class.java)

        setupRecyclerView()

        val onItemClick: (Long, String, String) -> Unit = { playlistId, playlistName, playlistImageUrl ->
            val intent = Intent(requireContext(), SongListActivity::class.java).apply {
                putExtra("playlistId", playlistId)
                putExtra("playlistName", playlistName)
                putExtra("playlistImageUrl", playlistImageUrl)
            }
            startActivity(intent)
        }

        recommendViewModel.tuijianGedan.observe(viewLifecycleOwner, { data ->
            val adapter = Mainrv1Adapter(data, onItemClick)
            binding.mainrv1.adapter = adapter
        })

        recommendViewModel.remenGedan.observe(viewLifecycleOwner, { data ->
            val adapter = Mainrv2Adapter(data, onItemClick)
            binding.mainrv2.adapter = adapter
        })

        recommendViewModel.banner.observe(viewLifecycleOwner, { bannerData ->
            val bannerAdapter = BannerAdapter(bannerData.data)
            binding.bannerViewPager.adapter = bannerAdapter
            binding.bannerViewPager.setPageTransformer(DepthAndZoomPageTransformer())

            handler.post(bannerAutoScrollRunnable)
        })

        recommendViewModel.newSongs.observe(viewLifecycleOwner, { newSongs ->
            val adapter = Mainvp4Adapter(newSongs.result)
            binding.mainvp.adapter = adapter
            binding.mainvp.setPageTransformer(DepthAndZoomPageTransformer())

            handler.post(mainvpAutoScrollRunnable)
        })

        recommendViewModel.fetchTuijianGedan()
        recommendViewModel.fetchRemenGedan()
        recommendViewModel.fetchBanner()
        recommendViewModel.fetchNewSongs()
    }

    private fun setupRecyclerView() {
        val layoutManager1 = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.mainrv1.layoutManager = layoutManager1

        val layoutManager2 = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.mainrv2.layoutManager = layoutManager2
    }

    override fun onDestroyView() {
        super.onDestroyView()
        handler.removeCallbacks(bannerAutoScrollRunnable)
        handler.removeCallbacks(mainvpAutoScrollRunnable)
        _binding = null
    }
}
