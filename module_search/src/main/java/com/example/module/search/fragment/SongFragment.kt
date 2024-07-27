package com.example.module.search.fragment

import SongViewModel
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.module.search.R
import com.example.module.search.adpter.SongAdapter
import com.example.module.search.bean.Search
import com.example.module.search.bean.Song
import com.example.module.search.databinding.FramentSongBinding
import com.example.module.search.viewmodel.SharedViewModel
import java.lang.NullPointerException

class SongFragment : Fragment() {
    private val songViewModel by lazy {
        ViewModelProvider(requireActivity())[SongViewModel::class.java]
    }
    private val sharedViewModel by lazy {
        ViewModelProvider(requireActivity())[SharedViewModel::class.java]
    }
    private var _mbinding: FramentSongBinding? = null
    private val mbinding get() = _mbinding!!
    private lateinit var songAdapter:SongAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _mbinding = FramentSongBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return mbinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        songAdapter=SongAdapter()
        val VISIBLE_THRESHOLD = 5

        try {

            songViewModel.songData.observe(viewLifecycleOwner, Observer { songs ->
                songs?.let {
                    songAdapter.submitList(it)
                    mbinding.refresh.isRefreshing=false
                }
            })

        } catch (e: NullPointerException) {
            e.printStackTrace()
            Log.d("fas", e.toString())
        }
        mbinding.rvSong.adapter = songAdapter
        mbinding.rvSong.layoutManager = LinearLayoutManager(context)
        mbinding.refresh.setOnRefreshListener {
            sharedViewModel.searchQuery.observe(viewLifecycleOwner, Observer { it ->
                it?.let {
                    songViewModel.getSongInfo(it)
                }

            })
        }
        mbinding.rvSong.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val totalItemCount = layoutManager.itemCount
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()

                if (totalItemCount <= (lastVisibleItem + VISIBLE_THRESHOLD)) {
                    sharedViewModel.searchQuery.observe(viewLifecycleOwner, Observer { it ->
                        it?.let {
                            songViewModel.getmoreSongs(it)
                            mbinding.refresh.isRefreshing=true
                        }

                    })


                }
            }
        })
    }

    override fun onDestroyView() {
        _mbinding = null
        super.onDestroyView()
    }
}