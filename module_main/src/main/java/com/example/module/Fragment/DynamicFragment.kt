package com.example.module.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.network.ViewModel.DynamicViewModel
import com.example.module.adapters.RvListAdapter
import com.example.module.main.databinding.FragmentDynamicBinding

class DynamicFragment : Fragment() {
    private var _binding: FragmentDynamicBinding? = null
    private val binding get() = _binding!!
    private val listViewmodel by lazy { ViewModelProvider(this)[DynamicViewModel::class.java] }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentDynamicBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
      listViewmodel.getSongInfo()
        initView()


    }

    private fun initView() {
        val rvListAdapter =RvListAdapter()
        listViewmodel.songData.observe(viewLifecycleOwner, Observer { data->
            data?.let {
                rvListAdapter.submitList(it)

            }

        })
        binding.rvListid.adapter=rvListAdapter
        binding.rvListid.layoutManager= GridLayoutManager(requireContext(), 2)


    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
