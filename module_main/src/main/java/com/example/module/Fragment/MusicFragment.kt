package com.example.module.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.module.main.databinding.FragmentMymusicBinding

class MusicFragment : Fragment() {
    private var _binding: FragmentMymusicBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding =  FragmentMymusicBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       initView()

    }

    private fun initView() {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
