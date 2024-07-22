package com.example.module.broadcast.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.module.broadcast.ViewModel.MvViewModel
import com.example.module.broadcast.ViewModel.MvdataViewModel
import com.example.module.broadcast.databinding.MvFragmentBinding
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.video.VideoSize

class MvFragment : Fragment() {
    val mvViewModel by lazy { ViewModelProvider(requireActivity())[MvViewModel::class.java] }
    val mvdataViewModel by lazy { ViewModelProvider(requireActivity())[MvdataViewModel::class.java] }
    val mbinding by lazy { MvFragmentBinding.inflate(layoutInflater) }
    val exoPlayer by lazy { ExoPlayer.Builder(requireContext()).build() }
    var commentid:Long=0
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return mbinding.root
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()

    }

    private fun initView() {
        mbinding.playerView.player = exoPlayer
        exoPlayer.addListener(object : Player.Listener {
            override fun onVideoSizeChanged(videoSize: VideoSize) {
                super.onVideoSizeChanged(videoSize)
                adjustPlayerViewSize()
            }
        })
        mvViewModel.songData.observe(viewLifecycleOwner, Observer { mvUrl ->
            mvUrl?.let {
                commentid=it[0].id
                initcommentClick()
                Log.d("newid",commentid.toString())


                val mediaItem = MediaItem.fromUri(it[0].url)
                exoPlayer.setMediaItem(mediaItem)
                exoPlayer.prepare()
                exoPlayer.playWhenReady = true
            }


        })
        mvdataViewModel.mdata.observe(viewLifecycleOwner, Observer { mvdata ->
            mvdata?.let {
                mbinding.goodCount.text = mvdata[0].subCount.toString()
                mbinding.shareCount.text = mvdata[0].shareCount.toString()
                mbinding.commentCount.text = mvdata[0].commentCount.toString()

            }

        })

    }



    private fun initcommentClick() {
        mbinding.commentButton.setOnClickListener {
            Log.d("comment","id"+commentid.toString())
      val commentFragment=CommentFragment.newInstance(commentid)
        commentFragment.show(childFragmentManager, "CommentFragment")
}
    }

    override fun onPause() {
        super.onPause()
        exoPlayer.pause()
    }

    override fun onResume() {
        super.onResume()
        exoPlayer.playWhenReady = true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        exoPlayer.release()
    }

    private fun adjustPlayerViewSize() {
        val playerViewLayoutParams =
            mbinding.playerView.layoutParams as ConstraintLayout.LayoutParams
        val heightRatio = 16 / 9f // 根据视频宽高比调整
        playerViewLayoutParams.width = ConstraintLayout.LayoutParams.MATCH_CONSTRAINT
        playerViewLayoutParams.height = ConstraintLayout.LayoutParams.WRAP_CONTENT
        mbinding.playerView.layoutParams = playerViewLayoutParams

        // 设置最大高度或计算实际高度
        val maxHeight = (resources.displayMetrics.heightPixels * 0.9).toInt() // 70% 屏幕高度
        if (playerViewLayoutParams.height > maxHeight) {
            playerViewLayoutParams.height = maxHeight
        }
        mbinding.playerView.layoutParams = playerViewLayoutParams
    }

    companion object {
        fun newInstance(): MvFragment {
            return MvFragment()
        }
    }
}