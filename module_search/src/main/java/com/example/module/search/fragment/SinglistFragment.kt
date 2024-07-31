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
    private var _mbinding:FragmentSinglistBinding?=null
    private val mbinding get() = _mbinding!!
    private lateinit var singlistAdapter :SinglistAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _mbinding=FragmentSinglistBinding.inflate(inflater,container,false)

        return mbinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
         singlistAdapter=SinglistAdapter()
        singlistViewModel.albumData.observe(viewLifecycleOwner, Observer { dataAlbum ->
            // 进行全面的空检查
            if (dataAlbum != null && dataAlbum.result != null) {
                val albums = dataAlbum.result.albums
                if (albums != null) {
                    singlistAdapter.submitList(albums)
                } else {
                    Log.d("SinglistFragment", "Albums is null")
                }
            } else {
                Log.d("SinglistFragment", "DataAlbum or result is null")
            }
        })

        mbinding.rvSinglist.adapter = singlistAdapter
        mbinding.rvSinglist.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onDestroyView() {
        _mbinding=null
        super.onDestroyView()
    }
}