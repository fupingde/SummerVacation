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
import com.example.module.search.adpter.SinglistAdapter
import com.example.module.search.bean.Album1
import com.example.module.search.databinding.FragmentSinglistBinding
import com.example.module.search.viewmodel.AlbumViewModel

class SinglistFragment : Fragment() {
    private val singlistViewModel by lazy {
        ViewModelProvider(requireActivity())[AlbumViewModel::class.java]
    }
    private val mbinding by lazy { FragmentSinglistBinding.inflate(layoutInflater) }
    private val singlistAdapter by lazy { SinglistAdapter() }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return mbinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        try {
            singlistViewModel.albumData.observe(viewLifecycleOwner, Observer { albums ->
                albums?.let {
                    Log.d("SingListFragment", "${it.result.albums}")
                    singlistAdapter.submitList(it.result.albums)
                }
            })
        } catch (e: Exception) {
            e.printStackTrace()
            Log.d("fas", e.toString())
        }

        mbinding.rvSinglist.adapter = singlistAdapter
        mbinding.rvSinglist.layoutManager = LinearLayoutManager(requireContext())
    }
}