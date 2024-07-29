package com.example.module.ui.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.launcher.ARouter
import com.bumptech.glide.Glide
import com.example.network.api.FragmentInterfacer
import com.example.module.adapters.MyVpAdapter
import com.example.module.adapters.MyrvAdapter
import com.example.module.database.MyDatabaseHelper
import com.example.module.main.databinding.FragmentMyBinding
import com.example.module.ui.activities.SongListActivity
import com.google.android.material.tabs.TabLayoutMediator

class MyFragment : Fragment() {
    private var _binding: FragmentMyBinding? = null
    private val binding get() = _binding!!
    private val REQUEST_PERMISSION_CODE = 1001
    private val PICK_IMAGE_REQUEST = 1
    private var login = false

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

    @SuppressLint("WrongConstant")
    private fun loadData() {
        var userId: Long = 0
        var userName: String? = null
        var imageUrl: String? = null
        var visitId: Long = 0

        val sharedPreferences = context?.getSharedPreferences("logindata", Context.MODE_PRIVATE)
        if (sharedPreferences != null) {
            userId = sharedPreferences.getLong("loginid", 0)
            userName = sharedPreferences.getString("usename", null)
            imageUrl = sharedPreferences.getString("imageurl", null)
            login = true
        }

        if (userId != 0L && !userName.isNullOrEmpty() && !imageUrl.isNullOrEmpty()) {
            binding.avatarName.text = userName
            Glide.with(requireActivity()).load(imageUrl).circleCrop().into(binding.nickImage)
            clickExchangeImage()
        } else {
            val sharedPreferencese =
                context?.getSharedPreferences("visitdata", Context.MODE_PRIVATE)
            if (sharedPreferencese != null) {
                visitId = sharedPreferencese.getLong("visitid", 0)
            }
            if (visitId != 0L) {
                binding.avatarName.text = "游客"
                initLoginClick()
            }
        }
    }

    @SuppressLint("WrongConstant")
    private fun initLoginClick() {
        binding.nickImage.setOnClickListener {
            val sharedPreferences = context?.getSharedPreferences("visitdata", Context.MODE_PRIVATE)
            val editor = sharedPreferences?.edit()
            editor?.clear()?.apply()
            ARouter.getInstance().build("/login/LoginActivity").navigation()
            // 清空所有数据
            requireActivity().finish()
        }
    }

    @SuppressLint("WrongConstant")
    private fun initClick() {
        binding.toolbarBtn2.setOnClickListener {
            val sharedPreferences = context?.getSharedPreferences("logindata", Context.MODE_PRIVATE)
            val editor = sharedPreferences?.edit()
            editor?.clear()?.apply()
            val sharedPreferences2 = context?.getSharedPreferences("visitdata", Context.MODE_PRIVATE)
            val visitEditor = sharedPreferences2?.edit()
            visitEditor?.clear()?.apply()
            ARouter.getInstance().build("/login/LoginActivity").navigation()
            requireActivity().finish()
        }

        binding.toolbarBtn1.setOnClickListener {
            ARouter.getInstance().build("/search/SearchActivity").navigation()
        }
    }

    private fun clickExchangeImage() {
        binding.nickImage.setOnClickListener {
            requestPermissions()
            openImagePicker()
        }
    }

    private fun requestPermissions() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                REQUEST_PERMISSION_CODE
            )
        }
    }

    private fun openImagePicker() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"

        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            val imageUri = data.data

            imageUri?.let {
                loadImage(it)
                Log.d("imageUri", it.toString())
            }
        }
    }

    @SuppressLint("WrongConstant")
    private fun loadImage(uri: Uri) {
        Log.d("MyFragment", "Loading image with URI: $uri")
        Glide.with(this).load(uri).circleCrop().into(binding.nickImage)
        val sharedPreferences = context?.getSharedPreferences("logindata", Context.MODE_PRIVATE)
        val editor = sharedPreferences?.edit()
        if (editor != null) {
            editor.putString("imageurl",uri.toString())
            editor.apply()
        }
    }
    private fun initView() {
        val dbHelper = MyDatabaseHelper(requireContext())
        val fragmentList = ArrayList<FragmentInterfacer>()
        initClick()
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

        binding.viewPagersr2.adapter = MyVpAdapter(requireActivity(), fragmentList)
        TabLayoutMediator(binding.tabLayoutsr, binding.viewPagersr2) { tab, position ->
            tab.text = when (position) {
                0 -> "音乐"
                1 -> "故事"
                else -> "频道"
            }
        }.attach()
        loadData()
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
