package com.example.module.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.Network.Bean.Songs
import com.example.Network.Retrofit
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import android.util.Log

class SongListViewModel : ViewModel() {

    private val _songs = MutableLiveData<Songs>()
    val songs: LiveData<Songs> get() = _songs

    private val _playlistName = MutableLiveData<String>()
    val playlistName: LiveData<String> get() = _playlistName

    private val compositeDisposable = CompositeDisposable()

    fun setPlaylistData(playlistId: Long, playlistName: String) {
        _playlistName.value = playlistName
        fetchSongs(playlistId)
    }

    private fun fetchSongs(playlistId: Long) {
        val disposable = Retrofit.apiService.getSongs(playlistId, 50, 0)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                _songs.value = response
                Log.d("SongListViewModel", "Songs fetched successfully: ${response.songs.size}")
            }, { error ->
                Log.e("SongListViewModel", "Error fetching songs", error)
                if (error is retrofit2.HttpException) {
                    Log.e("SongListViewModel", "HTTP Error: ${error.code()}")
                    Log.e("SongListViewModel", "HTTP Error Body: ${error.response()?.errorBody()?.string()}")
                }
            })
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}
