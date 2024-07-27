package com.example.module.broadcast.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.module.broadcast.ViewModel.MvViewModel
import com.example.module.broadcast.ViewModel.MvdataViewModel
import com.example.module.broadcast.ViewModel.OtherViewModel
import com.example.module.broadcast.databinding.MvFragmentBinding
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.example.module.broadcast.R

class OtherFragment : Fragment() {
    var _mbinding: MvFragmentBinding? = null
    val mbinding get() = _mbinding!!
     var ispush =false
    private lateinit var mvViewModel:MvViewModel
    private lateinit var mvdataViewModel:MvdataViewModel
    private lateinit var otherViewModel  :OtherViewModel
    val exoPlayer by lazy { ExoPlayer.Builder(requireContext()).build() }
    lateinit var url: String
    var commentid: Long = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _mbinding = MvFragmentBinding.inflate(inflater, container, false)
        return mbinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        mvViewModel= ViewModelProvider(this)[MvViewModel::class.java]
        mvdataViewModel=ViewModelProvider(this)[MvdataViewModel::class.java]
        otherViewModel=ViewModelProvider(this)[OtherViewModel::class.java]
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
        val mvid = arguments?.getLong(ARG_MV_ID)
        val mvorder = arguments?.getInt(ARG_MV_ORDER)

        mbinding.playerView.player = exoPlayer
        Log.d("mvid", "传入的id和order" + mvid + "," + mvorder)
        if (mvid != null) {
            otherViewModel.getSongInfo(mvid)
        }

        otherViewModel.OtehrMvid.observe(viewLifecycleOwner, Observer { id ->
            id?.let {
                if (mvorder != null) {
                    mvViewModel.getSongInfo(it[mvorder].id)
                    mvdataViewModel.getMvdata(it[mvorder].id)
                    commentid = it[mvorder].id
                    initClick()
                }
            }
        })

        mvViewModel.songData.observe(viewLifecycleOwner, Observer { mvurl ->
            mvurl?.let {
                if (mvorder != null) {
                    val mediaItem = MediaItem.fromUri(it[0].url)
                    url = it[0].url
                    initShareClick()
                    exoPlayer.setMediaItem(mediaItem)
                    exoPlayer.prepare()
                    exoPlayer.playWhenReady = true
                }
            }
        })

        mvdataViewModel.mdata.observe(viewLifecycleOwner, Observer { mvdata ->
            mvdata?.let {
                if (mvorder != null) {
                    mbinding.goodCount.text = mvdata[0].subCount.toString()
                    mbinding.shareCount.text = mvdata[0].shareCount.toString()
                    mbinding.commentCount.text = mvdata[0].commentCount.toString()
                }
            }
        })
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
        _mbinding = null
        exoPlayer.release()
    }

    companion object {
        private const val ARG_MV_ID = "mv_id"
        private const val ARG_MV_ORDER = "mv_order"

        fun newInstance(mvid: Long, order: Int): OtherFragment {
            val fragment = OtherFragment()
            val args = Bundle()
            args.putLong(ARG_MV_ID, mvid)
            args.putInt(ARG_MV_ORDER, order)
            fragment.arguments = args
            return fragment
        }
    }

    private fun initClick() {
        mbinding.commentButton.setOnClickListener {
            Log.d("comment", "id" + commentid.toString())
            val commentFragment = CommentFragment.newInstance(commentid)
            commentFragment.show(childFragmentManager, "CommentFragment")
        }
    }

    private fun initShareClick() {
        mbinding.shareButton.setOnClickListener {
            mvdataViewModel.mdata.observe(viewLifecycleOwner, Observer { date ->
                date?.let {
                    val mvnamead = it[0].name
                    val shareIntent = Intent().apply {
                        val title = "$mvnamead\n $url"
                        action = Intent.ACTION_SEND
                        putExtra(Intent.EXTRA_TEXT, title)
                        type = "text/plain"
                    }
                    startActivity(Intent.createChooser(shareIntent, "选择要分享的应用"))
                }
            })
        }
    }
}
