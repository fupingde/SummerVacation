package com.example.module.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.Network.Bean.Banner
import com.example.Network.Bean.NewSongs
import com.example.Network.Bean.ReMenGeDanBean
import com.example.Network.Bean.TuijianGedanBean
import com.example.Network.Bean.liuxinggedan
import com.example.Network.api.Retrofit
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class RecommendViewModel : ViewModel() {

    private val _tuijianGedan = MutableLiveData<TuijianGedanBean>()
    val tuijianGedan: LiveData<TuijianGedanBean> get() = _tuijianGedan

    private val _liuxingGedan = MutableLiveData<liuxinggedan>()
    val liuxinggedan: LiveData<liuxinggedan> get() = _liuxingGedan

    private val _remenGedan = MutableLiveData<ReMenGeDanBean>()
    val remenGedan: LiveData<ReMenGeDanBean> get() = _remenGedan

    private val _banner = MutableLiveData<Banner>()
    val banner: LiveData<Banner> get() = _banner

    private val _newSongs = MutableLiveData<NewSongs>()
    val newSongs: LiveData<NewSongs> get() = _newSongs

    fun fetchTuijianGedan() {
        Retrofit.apiService.getTuijianGedan(10)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                _tuijianGedan.value = response
            }, { error ->
                // Handle error
            })
    }
    fun fetchLiuXingGedan() {
        Retrofit.apiService.getLiuXingGeDan(1000)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                _liuxingGedan.value = response
            }, { error ->
                // Handle error
            })
    }

    fun fetchRemenGedan() {
        Retrofit.apiService.getRemenGedan(10)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                _remenGedan.value = response
            }, { error ->
                // Handle error
            })
    }

    fun fetchBanner() {
        Retrofit.apiService.getBanner()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                _banner.value = response
            }, { error ->
                // Handle error
            })
    }

    fun fetchNewSongs() {
        Retrofit.apiService.getnewSongs()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                _newSongs.value = response
            }, { error ->
                // Handle error
            })
    }
}
