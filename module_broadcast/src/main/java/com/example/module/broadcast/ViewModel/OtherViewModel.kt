package com.example.module.broadcast.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.module.broadcast.bean.Mv
import com.example.module.broadcast.bean.MvUrl
import com.example.module.broadcast.bean.OtherMvid
import com.example.module.broadcast.repository.GetOthermvid
import com.example.module.broadcast.repository.Getsongurl

class OtherViewModel : ViewModel() {

    private val _OtehrMvid: MutableLiveData<List<Mv>> = MutableLiveData()
    val OtehrMvid: LiveData<List<Mv>>
        get() = _OtehrMvid


    //得到单曲的信息
    fun getSongInfo(id:Long) {
        GetOthermvid.SearchSongs(id,_OtehrMvid)

    }


}