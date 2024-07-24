package com.example.singer.detail.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.singer.detail.databinding.FragemntSongBinding
import com.example.singer.detail.databinding.FragmentDetailBinding
import com.example.singer.detail.ui.ViewModel.SongViewModel

class DetailFragment:Fragment() {
    private val viewModel by lazy { ViewModelProvider(requireActivity())[SongViewModel::class.java] }
    private var _mbinding: FragmentDetailBinding?= null
    private val mbinding get() = _mbinding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _mbinding= FragmentDetailBinding.inflate(inflater, container, false)
        return mbinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
      initView()
    }

    private fun initView() {
        viewModel.imageData.observe(viewLifecycleOwner, Observer { data->
            data?.let {
                mbinding.detailText.text=it[0].briefDesc
            }

        })
    }

}
