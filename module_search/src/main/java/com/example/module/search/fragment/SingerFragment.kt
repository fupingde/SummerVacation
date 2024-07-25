package com.example.module.search.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.module.search.adpter.MvAdapter
import com.example.module.search.databinding.FragmentMvBinding
import com.example.module.search.viewmodel.MvViewModel

class SingerFragment :Fragment(){
    private val mvViewModel by lazy {
        ViewModelProvider(requireActivity())[MvViewModel::class.java]
    }
    private var _mbinding:FragmentMvBinding?=null
    private val mbinding get()=_mbinding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _mbinding = FragmentMvBinding.inflate(inflater, container, false)
        return mbinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       val mvAdapter=MvAdapter()
        mvViewModel.songData.observe(viewLifecycleOwner, Observer { mv->
            mv?.let {
                Log.d("mvdatas",it.toString())
                mvAdapter.submitList(it)
            }
        })
        mbinding.rvMv.adapter =mvAdapter
        mbinding.rvMv.layoutManager =LinearLayoutManager(requireContext())
    }

    override fun onDestroyView() {
        _mbinding=null
        super.onDestroyView()
    }

}