package com.example.module.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.Network.api.FragmentInterfacer
import com.example.module.adapters.MyVp2Adapter
import com.example.module.adapters.MyrvAdapter
import com.example.module.database.MyDatabaseHelper
import com.example.module.main.databinding.FragmentMyBinding
import com.example.module.ui.activities.SongListActivity
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
        val dbHelper = MyDatabaseHelper(requireContext())
        val fragmentList = ArrayList<FragmentInterfacer>()
        fragmentList.add(object : FragmentInterfacer {
            override fun back(): Fragment {
                return MusicFragment()
            }
        })
        fragmentList.add(object : FragmentInterfacer {
            override fun back(): Fragment {
                return StoryFragment()
            }
        })
        fragmentList.add(object : FragmentInterfacer {
            override fun back(): Fragment {
                return ChannelFragment()
            }
        })

        binding.viewPagersr2.adapter = MyVp2Adapter(requireActivity(), fragmentList)
        TabLayoutMediator(binding.tabLayoutsr, binding.viewPagersr2) { tab, position ->
            tab.text = when (position) {
                0 -> "音乐"
                1 -> "故事"
                else -> "频道"
            }
        }.attach()

        setupRecyclerView(dbHelper)
    }

    private fun setupRecyclerView(dbHelper: MyDatabaseHelper) {
        val myrvBean = dbHelper.getAllCollectedPlaylists()

        val onItemClick: (Long, String, String) -> Unit = { playlistId, playlistName, playlistImageUrl ->
            val intent = Intent(requireContext(), SongListActivity::class.java).apply {
                putExtra("playlistId", playlistId)
                putExtra("playlistName", playlistName)
                putExtra("playlistImageUrl", playlistImageUrl)
            }
            startActivity(intent)
        }

        val adapter = MyrvAdapter(myrvBean, onItemClick)
        binding.mineRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.mineRecyclerView.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
