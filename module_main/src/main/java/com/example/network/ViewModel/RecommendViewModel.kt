package com.example.module.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.network.Bean.Banner
import com.example.network.Bean.FashionlistBean
import com.example.network.Bean.HotlistBean
import com.example.network.Bean.NewSongs
import com.example.network.Bean.RecommendlistBean
import com.example.network.api.Retrofit
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class RecommendViewModel : ViewModel() {

    private val _recommendlist = MutableLiveData<RecommendlistBean>()
    val recommendlist: LiveData<RecommendlistBean> get() = _recommendlist

    private val _fashionlist = MutableLiveData<FashionlistBean>()
    val fashionlist: LiveData<FashionlistBean> get() = _fashionlist

    private val _hotlist = MutableLiveData<HotlistBean>()
    val hotlist: LiveData<HotlistBean> get() = _hotlist

    private val _banner = MutableLiveData<Banner>()
    val banner: LiveData<Banner> get() = _banner

    private val _newSongs = MutableLiveData<NewSongs>()
    val newSongs: LiveData<NewSongs> get() = _newSongs

    fun fetchRecommendlist() {
        Retrofit.apiService.getRecommendlist(10)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                _recommendlist.value = response
            }, { error ->
                // Handle error
            })
    }
    fun fetchFashionlist() {
        Retrofit.apiService.getFashionlist(1000)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                _fashionlist.value = response
            }, { error ->
                // Handle error
            })
    }

    fun fetchHotlist() {
        Retrofit.apiService.getHotlist(10)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                _hotlist.value = response
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
