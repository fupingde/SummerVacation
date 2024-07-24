package com.example.module.ui.custom

import androidx.viewpager2.widget.ViewPager2
import android.view.View

class SmoothScrollPageTransformer : ViewPager2.PageTransformer {
    override fun transformPage(view: View, position: Float) {
        view.apply {
            when {
                position < -1 -> { // [-Infinity,-1)
                    alpha = 0f
                }
                position <= 0 -> { // [-1,0]
                    alpha = 1f
                    translationX = 0f
                    translationZ = 0f
                    scaleX = 1f
                    scaleY = 1f
                }
                position <= 1 -> { // (0,1]
                    // 淡入淡出效果
                    alpha = 1 - position

                    // 缩小效果
                    val scaleFactor = 0.85f + (1 - 0.85f) * (1 - Math.abs(position))
                    scaleX = scaleFactor
                    scaleY = scaleFactor

                    // 深度效果
                    translationX = view.width * -position
                    translationZ = -1f
                }
                else -> { // (1,+Infinity]
                    alpha = 0f
                }
            }
        }
    }
}
