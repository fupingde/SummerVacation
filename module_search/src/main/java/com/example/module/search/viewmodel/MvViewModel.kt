package com.example.module.search.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.module.search.bean.Song
import com.example.module.search.bean.mv.Mv
import com.example.module.search.repository.MvRepository

class MvViewModel:ViewModel() {
    private val _mvData: MutableLiveData<List<Mv>> = MutableLiveData()
    val songData: LiveData<List<Mv>>
        get() = _mvData
fun getMvidata(keywords:String){
    MvRepository.SearchSongs(keywords,_mvData)
}


}