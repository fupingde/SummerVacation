package com.example.module.broadcast.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.module.broadcast.ViewModel.MvViewModel
import com.example.module.broadcast.ViewModel.MvdataViewModel
import com.example.module.broadcast.databinding.MvFragmentBinding
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.VideoSize
import androidx.media3.exoplayer.ExoPlayer
import com.example.module.broadcast.R

class MvFragment : Fragment() {
    val mvViewModel by lazy { ViewModelProvider(requireActivity())[MvViewModel::class.java] }
    val mvdataViewModel by lazy { ViewModelProvider(requireActivity())[MvdataViewModel::class.java] }
    var _mbinding: MvFragmentBinding? = null
    val mbinding get() = _mbinding!!
    var commentid: Long = 0
    lateinit var url: String
    var ispush=false
    lateinit var exoPlayer: ExoPlayer

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _mbinding = MvFragmentBinding.inflate(inflater, container, false)
        return mbinding.root
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        initCommentClick()
        mbinding.likeButton.setOnClickListener {
           if (ispush===false){
           mbinding.likeButton.setImageResource(R.drawable.ic_ispush)
            ispush=true
           }
            else{
               mbinding.likeButton.setImageResource(R.drawable.ic_push)
               ispush=false
           }
        }
        exoPlayer = ExoPlayer.Builder(requireContext()).build()
        exoPlayer.addListener(object : Player.Listener {
            override fun onVideoSizeChanged(videoSize: VideoSize) {
                super.onVideoSizeChanged(videoSize)
                adjustPlayerViewSize()
            }
        })
        mbinding.playerView.player = exoPlayer

        mvViewModel.songData.observe(viewLifecycleOwner, Observer { mvUrl ->
            mvUrl?.let {
                val mediaItemUrl = it[0].url
                url = it[0].url
                initShareClick()
                if (mediaItemUrl.isNullOrEmpty()) {
                    Log.e("MvFragment", "Invalid media item URL")
                    return@Observer
                }
                Log.d("MvFragment", "Setting media item URL: $mediaItemUrl")
                val mediaItem = MediaItem.fromUri(mediaItemUrl)
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

    private fun initShareClick() {
        mbinding.shareButton.setOnClickListener {
            mvdataViewModel.mdata.observe(viewLifecycleOwner, Observer { date ->
                date?.let {
                    val mvNameAd = it[0].name
                    val shareIntent = Intent().apply {
                        val title = "$mvNameAd\n $url"
                        action = Intent.ACTION_SEND
                        putExtra(Intent.EXTRA_TEXT, title)
                        type = "text/plain"
                    }
                    startActivity(Intent.createChooser(shareIntent, "选择要分享的应用"))
                }
            })
        }
    }

    private fun initCommentClick() {
        mbinding.commentButton.setOnClickListener {
            mvViewModel.songData.observe(viewLifecycleOwner, Observer { mvUrl ->
                mvUrl?.let {
                    commentid = it[0].id
                    Log.d("comment", "id" + commentid.toString())
                    val commentFragment = CommentFragment.newInstance(commentid)
                    commentFragment.show(childFragmentManager, "CommentFragment")
                }
            })
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
