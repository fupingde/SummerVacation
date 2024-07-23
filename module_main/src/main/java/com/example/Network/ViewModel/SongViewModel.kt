package com.example.module.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class SongViewModel(application: Application) : AndroidViewModel(application) {
    private val _songId = MutableLiveData<Long>()
    val songId: LiveData<Long> get() = _songId

    private val _songName = MutableLiveData<String>()
    val songName: LiveData<String> get() = _songName

    private val _songArtist = MutableLiveData<String>()
    val songArtist: LiveData<String> get() = _songArtist

    private val _songPictureUrl = MutableLiveData<String>()
    val songPictureUrl: LiveData<String> get() = _songPictureUrl

    fun updateSongData(id: Long, name: String, artist: String, pictureUrl: String) {
        _songId.value = id
        _songName.value = name
        _songArtist.value = artist
        _songPictureUrl.value = pictureUrl
    }

}
