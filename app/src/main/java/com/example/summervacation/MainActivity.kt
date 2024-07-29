package com.example.summervacation

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.launcher.ARouter
import com.example.module.main.databinding.ActivityMainBinding
import com.example.summervacation.databinding.ActivityMainsappBinding

class MainActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityMainsappBinding.inflate(layoutInflater)
    }
    private val adDuration: Long = 5000 // 5 seconds
    private val handler = Handler(Looper.getMainLooper())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        binding.skipButton.setOnClickListener {
            navigateToMain()
        }
        // Delay to navigate to main activity
        handler.postDelayed({
            navigateToMain()
        }, adDuration)
    }

    private fun navigateToMain() {
        if (!isFinishing) {
            handler.removeCallbacksAndMessages(null)
            ARouter.getInstance().build("/login/LoginActivity")
                .navigation()
            finish()
        }
    }







    }



