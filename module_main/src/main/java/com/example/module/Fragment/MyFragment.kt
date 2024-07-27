package com.example.module.ui.fragments

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.launcher.ARouter
import com.bumptech.glide.Glide
import com.example.Network.api.FragmentInterfacer
import com.example.module.adapters.MyVp2Adapter
import com.example.module.main.databinding.FragmentMyBinding
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

    override fun onResume() {
        super.onResume()
        // 在这里重新加载数据
        loadData()
    }

    private fun initView() {
        initClick()
        val fragmentList = arrayListOf<FragmentInterfacer>(
            object : FragmentInterfacer {
                override fun back(): Fragment {
                    return MusicFragment()
                }
            },
            object : FragmentInterfacer {
                override fun back(): Fragment {
                    return StoryFragment()
                }
            },
            object : FragmentInterfacer {
                override fun back(): Fragment {
                    return ChannelFragment()
                }
            }
        )
        binding.viewPagersr2.adapter = MyVp2Adapter(requireActivity(), fragmentList)
        TabLayoutMediator(binding.tabLayoutsr, binding.viewPagersr2) { tab, position ->
            tab.text = when (position) {
                0 -> "音乐"
                1 -> "故事"
                else -> "频道"
            }
        }.attach()
    }

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

    private fun initLoginClick() {
        binding.nickImage.setOnClickListener {
            val sharedPreferences = context?.getSharedPreferences("visitdata", Context.MODE_PRIVATE)
            val editor = sharedPreferences?.edit()
            editor?.clear()?.apply()
            ARouter.getInstance().build("/login/LoginActivity").navigation()
            // 清空所有数据
        }
    }

    private fun initClick() {
        binding.toolbarBtn2.setOnClickListener {
            val sharedPreferences = context?.getSharedPreferences("logindata", Context.MODE_PRIVATE)
            val editor = sharedPreferences?.edit()
            editor?.clear()?.apply()
            ARouter.getInstance().build("/login/LoginActivity").navigation()
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
            }
        }
    }

    private fun loadImage(uri: Uri) {
        Glide.with(this).load(uri).circleCrop().into(binding.nickImage)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
