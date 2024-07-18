package com.example.module.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.RecyclerView

class NestedScrollableHost(context: Context, attrs: AttributeSet) : NestedScrollView(context, attrs) {

    private var childRecyclerView: RecyclerView? = null

    init {
        isFillViewport = true
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        return super.onInterceptTouchEvent(ev).also {
            handleTouchEvent(ev)
        }
    }

    override fun onTouchEvent(ev: MotionEvent): Boolean {
        return super.onTouchEvent(ev).also {
            handleTouchEvent(ev)
        }
    }

    private fun handleTouchEvent(event: MotionEvent) {
        if (childRecyclerView == null) {
            childRecyclerView = findRecyclerView(this)
        }

        childRecyclerView?.let {
            if (event.action == MotionEvent.ACTION_DOWN) {
                it.stopScroll()
            }

            if (!it.canScrollVertically(-1) && !it.canScrollVertically(1)) {
                requestDisallowInterceptTouchEvent(true)
            }
        }
    }

    private fun findRecyclerView(view: View): RecyclerView? {
        if (view is RecyclerView) {
            return view
        }

        if (view is ViewGroup) {
            for (i in 0 until view.childCount) {
                findRecyclerView(view.getChildAt(i))?.let {
                    return it
                }
            }
        }

        return null
    }
}
