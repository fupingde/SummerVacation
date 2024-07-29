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
import com.example.module.ui.adapters.MainrecommendrvAdapter
import com.example.module.ui.adapters.MainhotrvAdapter
import com.example.module.ui.adapters.MainbottomvpAdapter
import com.example.module.ui.viewmodel.RecommendViewModel
import com.example.module.ui.adapters.MainFashionAdapter
import com.example.module.ui.custom.SmoothScrollPageTransformer

class RecommendContentFragment : Fragment() {

    private var _binding: FragmentRecommendContentBinding? = null
    private val binding get() = _binding!!

    private lateinit var recommendViewModel: RecommendViewModel

    private val handler = Handler(Looper.getMainLooper())
    private val autoScrollRunnable = object : Runnable {
        override fun run() {
            val itemCount = binding.mainbottomvp.adapter?.itemCount ?: 0
            if (itemCount > 0) {
                val nextItem = (binding.mainbottomvp.currentItem + 1) % itemCount
                binding.mainbottomvp.currentItem = nextItem
            }
            handler.postDelayed(this, 6000)
        }
    }

    private val bannerAutoScrollRunnable = object : Runnable {
        override fun run() {
            val itemCount = binding.bannerViewPager.adapter?.itemCount ?: 0
            if (itemCount > 0) {
                val nextItem = (binding.bannerViewPager.currentItem + 1) % itemCount
                binding.bannerViewPager.currentItem = nextItem
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

        recommendViewModel.recommendlist.observe(viewLifecycleOwner) { data ->
            val adapter = MainrecommendrvAdapter(data, onItemClick)
            binding.mainrecommendrv.adapter = adapter
        }

        recommendViewModel.hotlist.observe(viewLifecycleOwner) { data ->
            val adapter = MainhotrvAdapter(data, onItemClick)
            binding.mainhotrv.adapter = adapter
        }

        recommendViewModel.fashionlist.observe(viewLifecycleOwner) { data ->
            val adapter = MainFashionAdapter(data, onItemClick)
            binding.mainfashionrv.adapter = adapter
        }

        recommendViewModel.banner.observe(viewLifecycleOwner) { bannerData ->
            val bannerAdapter = BannerAdapter(bannerData.banners)
            binding.bannerViewPager.adapter = bannerAdapter
            binding.bannerViewPager.setPageTransformer(SmoothScrollPageTransformer())
            handler.post(bannerAutoScrollRunnable)
        }

        recommendViewModel.newSongs.observe(viewLifecycleOwner) { newSongs ->
            val adapter = MainbottomvpAdapter(requireContext(), newSongs.result)
            binding.mainbottomvp.adapter = adapter
            binding.mainbottomvp.setPageTransformer(SmoothScrollPageTransformer())
            handler.post(autoScrollRunnable)
        }

        recommendViewModel.fetchRecommendlist()
        recommendViewModel.fetchHotlist()
        recommendViewModel.fetchBanner()
        recommendViewModel.fetchNewSongs()
        recommendViewModel.fetchFashionlist()
    }

    private fun setupRecyclerView() {
        val layoutManager1 = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.mainrecommendrv.layoutManager = layoutManager1

        val layoutManager2 = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.mainhotrv.layoutManager = layoutManager2

        val layoutManager3 = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.mainfashionrv.layoutManager = layoutManager3
    }

    override fun onDestroyView() {
        super.onDestroyView()
        handler.removeCallbacks(autoScrollRunnable)
        handler.removeCallbacks(bannerAutoScrollRunnable)
        _binding = null
    }
}
