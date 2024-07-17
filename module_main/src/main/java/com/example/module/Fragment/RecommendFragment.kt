package com.example.module.ui.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.Network.ViewModel.RecommendViewModel
import com.example.module.main.databinding.FragmentRecommendBinding
import com.example.module.ui.adapters.Mainrv1Adapter
import com.example.module.ui.viewmodel.RecommendViewModel

class RecommendFragment : Fragment() {

    private var _binding: FragmentRecommendBinding? = null
    private val binding get() = _binding!!

    private val recommendViewModel: RecommendViewModel by viewModels()

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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentRecommendBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        recommendViewModel.tuijianGedan.observe(viewLifecycleOwner, Observer { data ->
            val adapter = Mainrv1Adapter(data)
            binding.mainrv1.adapter = adapter

            handler.post(autoScrollRunnable)
        })

        recommendViewModel.fetchTuijianGedan()
    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.mainrv1.layoutManager = layoutManager
    }

    override fun onDestroyView() {
        super.onDestroyView()
        handler.removeCallbacks(autoScrollRunnable)
        _binding = null
    }
}
