package com.example.singer.detail.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.singer.detail.databinding.FragemntSongBinding
import com.example.singer.detail.ui.Adapter.RvAdapter
import com.example.singer.detail.ui.ViewModel.SongViewModel

class SongFragment : Fragment() {
    private val viewModel by lazy { ViewModelProvider(requireActivity())[SongViewModel::class.java] }
    private var _mbinding: FragemntSongBinding? = null
    private val mbinding get() = _mbinding!!
    private lateinit var songAdapter: RvAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _mbinding = FragemntSongBinding.inflate(inflater, container, false)
        return mbinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        songAdapter = RvAdapter()
        mbinding.rv.adapter = songAdapter
        mbinding.rv.layoutManager = LinearLayoutManager(context)
        viewModel.songData.observe(viewLifecycleOwner, Observer { data ->
            data?.let {
                if (it.isNotEmpty()) {
                    Log.d("传入数据", it[0].toString())
                    songAdapter.submitList(it)
                } else {
                    Log.d("传入数据", "列表为空")
                }
            } ?: run {
                Log.d("传入数据", "数据为空")
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _mbinding = null
    }
}
