package com.example.module.broadcast.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.module.broadcast.bean.MvUrl
import com.example.module.broadcast.repository.Getsongurl

class MvViewModel : ViewModel() {

    private val _songData: MutableLiveData<List<MvUrl.Data>> = MutableLiveData()
    val songData: LiveData<List<MvUrl.Data>>
        get() = _songData


    //得到单曲的信息
    fun getSongInfo(id:Long) {
        Getsongurl.SearchSongs(id,_songData)

    }


}