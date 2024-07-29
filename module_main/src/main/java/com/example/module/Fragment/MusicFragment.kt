package com.example.module.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.module.adapters.MyrvAdapter
import com.example.module.database.MyDatabaseHelper
import com.example.module.main.databinding.FragmentMymusicBinding
import com.example.module.ui.MycollectedActivity
import com.example.module.ui.MydownloadActivity

class MusicFragment : Fragment() {
    private var _binding: FragmentMymusicBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentMymusicBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        val dbHelper = MyDatabaseHelper(requireContext())

        val collectedCount = dbHelper.getCollectedCount()
        val downloadedCount = dbHelper.getDownloadedCount()

//        binding.shoucangCount.text = collectedCount.toString()
//        binding.downloadcount.text = downloadedCount.toString()

        binding.mycollected.setOnClickListener {
            val intent = Intent(activity, MycollectedActivity::class.java)
            startActivity(intent)
        }
        binding.mydownload.setOnClickListener {
            val intent = Intent(activity, MydownloadActivity::class.java)
            startActivity(intent)
        }


    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
