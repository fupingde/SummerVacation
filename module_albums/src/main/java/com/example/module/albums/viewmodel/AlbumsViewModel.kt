package com.example.module.albums.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.module.albums.bean.Album
import com.example.module.albums.bean.AlubumsList
import com.example.module.albums.bean.Song
import com.example.module.albums.resposiry.GetAlbums

class AlbumsViewModel:ViewModel() {
    private val _songData: MutableLiveData<List<Song>> = MutableLiveData()
    val songData: LiveData<List<Song>>
        get() = _songData
    private val _imageData: MutableLiveData<List<Album>> = MutableLiveData()
    val imageData: LiveData<List<Album>>
        get() = _imageData


    fun getSongIn(id:Long){
        GetAlbums.SearchSongs(id,_songData,_imageData)

    }
}