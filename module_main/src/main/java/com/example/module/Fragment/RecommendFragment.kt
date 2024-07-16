package com.example.module.ui.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.module.main.databinding.FragmentRecommendBinding
import com.example.module.ui.adapters.Mainrv1Adapter
import com.example.Network.Bean.TuijianGedanBean
import com.example.Network.Retrofit
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class RecommendFragment : Fragment() {
    private var _binding: FragmentRecommendBinding? = null
    private val binding get() = _binding!!

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
            handler.postDelayed(this, 3000) // 自动滚动间隔时间（毫秒）
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentRecommendBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fetchData()
    }

    private fun fetchData() {
        Retrofit.apiService.getTuijianGedan(10)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                setupRecyclerView(response)
            }, { error ->
                Log.e("RecommendFragment", "Error fetching data", error)
            })
    }

    private fun setupRecyclerView(data: TuijianGedanBean) {
        val adapter = Mainrv1Adapter(data)
        binding.mainrv1.adapter = adapter

        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.mainrv1.layoutManager = layoutManager

        // 启动自动滚动
        handler.post(autoScrollRunnable)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // 停止自动滚动
        handler.removeCallbacks(autoScrollRunnable)
        _binding = null
    }
}
