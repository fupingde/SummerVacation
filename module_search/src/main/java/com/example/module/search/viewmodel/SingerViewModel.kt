package com.example.module.search.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.module.search.bean.singer.Artist
import com.example.module.search.repository.SingerRepository

class SingerViewModel :ViewModel() { private val _singerData: MutableLiveData<List<Artist>> = MutableLiveData()
    val singerData: LiveData<List<Artist>>
        get() = _singerData


    //得到单曲的信息
    fun getSongInfo(keyword: String) {
        SingerRepository.SearchSongs(keyword,_singerData)

    }


}