package com.example.module.ui.viewmodel

import android.app.Application

object ViewModelSingleton {
    private var songViewModel: SongViewModel? = null

    fun getSongViewModel(application: Application): SongViewModel {
        if (songViewModel == null) {
            songViewModel = SongViewModel(application)
        }
        return songViewModel!!
    }
}
