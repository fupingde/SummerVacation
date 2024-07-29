package com.example.network.SingletionClass

import android.app.Application
import com.example.module.ui.viewmodel.SongViewModel

object ViewModelSingleton {
    private var songViewModel: SongViewModel? = null

    fun getSongViewModel(application: Application): SongViewModel {
        if (songViewModel == null) {
            songViewModel = SongViewModel(application)
        }
        return songViewModel!!
    }
}
