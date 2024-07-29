package com.example.module.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.module.main.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.network.Bean.Song2
import com.example.module.ui.adapters.PlaylistAdapter
import com.google.android.material.bottomsheet.BottomSheetBehavior

class PlaylistBottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PlaylistAdapter
    private lateinit var songs: List<Song2>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            songs = it.getSerializable("PLAYLIST_SONGS") as List<Song2>
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.bottom_sheet_playlist, container, false)

        recyclerView = view.findViewById(R.id.playlistRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)

        adapter = PlaylistAdapter(songs) { song, songs, position ->
            Log.d("PlaylistBottomSheetFragment", "Clicked on song: ${song.name} at position $position")
        }
        recyclerView.adapter = adapter

        return view
    }

    override fun onStart() {
        super.onStart()
        val bottomSheet = dialog?.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
        bottomSheet?.layoutParams?.height = requireContext().resources.displayMetrics.heightPixels / 2
        bottomSheet?.layoutParams = bottomSheet?.layoutParams
        val behavior = BottomSheetBehavior.from(bottomSheet!!)
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    companion object {
        @JvmStatic
        fun newInstance(songs: List<Song2>): PlaylistBottomSheetFragment {
            return PlaylistBottomSheetFragment().apply {
                arguments = Bundle().apply {
                    putSerializable("PLAYLIST_SONGS", ArrayList(songs))
                }
            }
        }
    }
}
