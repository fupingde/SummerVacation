package com.example.module.broadcast.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.module.broadcast.ViewModel.CommentViewModel
import com.example.module.broadcast.adapter.CommentAdapter
import com.example.module.broadcast.databinding.FragmentCommentsBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class CommentFragment: BottomSheetDialogFragment() {
    private var _mbinding: FragmentCommentsBinding? = null
    private val mbinding get() = _mbinding!!
    private val commentViewModel by lazy { ViewModelProvider(this)[CommentViewModel::class.java] }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _mbinding = FragmentCommentsBinding.inflate(inflater, container, false)
        return mbinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
         val id= arguments?.getLong(ARG_ID)
        Log.d("commentid",id.toString())
        if (id != null) {
            commentViewModel.getComment(id)
        }
        initView()

    }

    private fun initView() {
        val commentAdapter = CommentAdapter()
        mbinding.commentsRV.layoutManager = LinearLayoutManager(context)
        commentViewModel.commentdata.observe(viewLifecycleOwner, Observer { data ->

            data?.let {
                mbinding.commentsTitle.text="评论数:"+ commentViewModel.getCommentszie().toString()
                commentAdapter.submitList(it)
            }
        })
        mbinding.commentsRV.adapter = commentAdapter
    }

    companion object {
        private const val ARG_ID = "id"

        @JvmStatic
        fun newInstance(id: Long) =
            CommentFragment().apply {
                arguments = Bundle().apply {
                    putLong(ARG_ID, id)
                }
            }

    }
}