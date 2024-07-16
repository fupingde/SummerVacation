package com.example.module.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.module.main.R
import com.example.module.main.databinding.ActivityMainBinding
import com.example.module.ui.fragments.DynamicFragment
import com.example.module.ui.fragments.RecommendFragment
import com.example.module.ui.fragments.MyFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_recommend -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, RecommendFragment())
                        .commit()
                    true
                }
                R.id.navigation_dynamic -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, DynamicFragment())
                        .commit()
                    true
                }
                R.id.navigation_my -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, MyFragment())
                        .commit()
                    true
                }
                else -> false
            }
        }

        // 加载默认的 Fragment
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, RecommendFragment())
                .commit()
        }
    }
}
