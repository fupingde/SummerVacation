package com.example.module.broadcast.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.module.broadcast.bean.Onecomment
import com.example.module.broadcast.repository.GetComment

class CommentViewModel : ViewModel() {
    private val _commentdata: MutableLiveData<List<Onecomment>> = MutableLiveData()
    val commentdata: LiveData<List<Onecomment>>
        get() = _commentdata
    var currentPage = 1

    fun getComment(id: Long) {
        GetComment.getComment(id, _commentdata)
    }

    fun getCommentszie(): Int? {
        return _commentdata.value?.size

    }

    fun getmore(id: Long) {
        GetComment.getmore(id, currentPage, _commentdata)
        currentPage++
    }


}