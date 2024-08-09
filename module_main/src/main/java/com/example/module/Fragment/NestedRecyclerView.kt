package com.example.module.Fragment

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.media3.common.util.Log
import androidx.recyclerview.widget.RecyclerView

class NestedRecyclerView(context: Context, attrs: AttributeSet) : RecyclerView(context, attrs) {

    private var x1 = 0f
    private var y1 = 0f
    private var isScroll = false

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(evt: MotionEvent?): Boolean {
        evt?.let {
            var str = ""
            when (evt.action) {
                MotionEvent.ACTION_DOWN -> {
                    x1 = evt.x
                    y1 = evt.y
                }

                MotionEvent.ACTION_MOVE -> if (this.childCount > 1) {
                    // 通知其父控件不拦截
                    val x2 = evt.x
                    val y2 = evt.y
                    if (x1 - x2 > 1) {
                        str = "向右滑"
                        parent.requestDisallowInterceptTouchEvent(true)
                        isScroll = false
                    } else if (x2 - x1 > 1) {
                        str = "向左滑"
                        parent.requestDisallowInterceptTouchEvent(true)
                        isScroll = false
                    } else if (y1 - y2 > 100) {
                        parent.requestDisallowInterceptTouchEvent(false)
                        str = "向上滑"
                        isScroll = true
                    } else if (y2 - y1 > 100) {
                        parent.requestDisallowInterceptTouchEvent(false)
                        str = "向下滑"
                        isScroll = true
                    }
                    return if (isScroll) {
                        true
                    } else {
                        super.onTouchEvent(evt)
                    }
                }

                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    if (isScroll) {
                        parent.requestDisallowInterceptTouchEvent(false)
                    } else {
                        parent.requestDisallowInterceptTouchEvent(true)
                    }
                    return if (isScroll) {
                        true
                    } else {
                        super.onTouchEvent(evt)
                    }
                }
            }
        }
        return super.onTouchEvent(evt)
    }
}