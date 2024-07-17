package com.example.module.ui.transformers
import android.view.View
import androidx.viewpager2.widget.ViewPager2

class DepthPageTransformer : ViewPager2.PageTransformer {
    override fun transformPage(view: View, position: Float) {
        view.apply {
            when {
                position < -1 -> { // [-Infinity, -1)
                    alpha = 0f
                }
                position <= 0 -> { // [-1, 0]
                    alpha = 1f
                    translationX = 0f
                    scaleX = 1f
                    scaleY = 1f
                }
                position <= 1 -> { // (0, 1]
                    alpha = 1 - position
                    translationX = view.width * -position
                    val scaleFactor = 0.75f + (1 - 0.75f) * (1 - Math.abs(position))
                    scaleX = scaleFactor
                    scaleY = scaleFactor
                }
                else -> { // (1, +Infinity]
                    alpha = 0f
                }
            }
        }
    }
}
