package com.example.module.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.Network.api.FragmentInterfacer
import com.example.module.adapters.MyVp2Adapter
import com.example.module.main.databinding.FragmentMyBinding
import com.google.android.material.tabs.TabLayoutMediator

class MyFragment : Fragment() {
    private var _binding: FragmentMyBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()

    }

    private fun initView() {
        val fragmentLsit = ArrayList<FragmentInterfacer>()
        fragmentLsit.let {
            it.add(object : FragmentInterfacer {
                override fun back(): Fragment {
                    return MusicFragment()
                }
            })
            it.add(object : FragmentInterfacer {
                override fun back(): Fragment {
                    return StoryFragment()
                }
            })
            it.add(object : FragmentInterfacer {
                override fun back(): Fragment {
                    return ChannelFragment()
                }
            })
        }
        binding.viewPagersr2.adapter=MyVp2Adapter(requireActivity(),fragmentLsit)
        TabLayoutMediator(binding.tabLayoutsr,binding.viewPagersr2){tab,positon ->
            tab.text=when(positon){
                0->"音乐"
                1->"故事"
                else->"频道"
            }

        }.attach()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}