package com.example.singer.detail.ui.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.singer.detail.ui.bean.ArtistX
import com.example.singer.detail.ui.bean.Song
import com.example.singer.detail.ui.resoposiry.GetSong

class SongViewModel:ViewModel() {
    private val _songData: MutableLiveData<List<Song>> = MutableLiveData()
    val songData: LiveData<List<Song>>
        get() = _songData
private val _imageData: MutableLiveData<List<ArtistX>> = MutableLiveData()
    val imageData: LiveData<List<ArtistX>>
        get() = _imageData


    fun getSongIn(id:Long){
        GetSong.SearchSongs(id,_songData,_imageData)

    }

}