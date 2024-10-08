package com.example.module.search.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.module.search.R
import com.example.module.search.adpter.SingerAdapter
import com.example.module.search.databinding.FragmentMvBinding
import com.example.module.search.viewmodel.SingerViewModel

class MvFragment :Fragment(){
    private val singerViewModel by lazy {
        ViewModelProvider(requireActivity())[SingerViewModel::class.java]
    }
    private var _mBinding:FragmentMvBinding ?=null
    private val mBinding get() = _mBinding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _mBinding=FragmentMvBinding.inflate(inflater,container,false)
        // Inflate the layout for this fragment
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        singerViewModel.singerData.observe(viewLifecycleOwner) {
            mBinding.rvMv.adapter = SingerAdapter(it)
            mBinding.rvMv.layoutManager = LinearLayoutManager(context)

        }
    }
    override fun onDestroyView() {
       _mBinding=null
        super.onDestroyView()
    }
}