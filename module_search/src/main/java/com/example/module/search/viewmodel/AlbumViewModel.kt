package com.example.module.search.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.module.search.bean.Album
import com.example.module.search.bean.Album1
import com.example.module.search.bean.DataAlbum
import com.example.module.search.bean.Song
import com.example.module.search.repository.AlbumRepository
import com.example.module.search.repository.SongRepository

class AlbumViewModel : ViewModel(){
    private val _albumData: MutableLiveData<DataAlbum> = MutableLiveData()

    val albumData: LiveData<DataAlbum>
        get() = _albumData


    //得到单曲的信息
    fun getSongInfo(keyword: String) {
        AlbumRepository.SearchSongs(keyword,10,_albumData)

    }



}