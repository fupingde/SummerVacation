package com.example.module.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.Network.Bean.Banner
import com.example.Network.Bean.TuijianGedanBean
import com.example.Network.Retrofit
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class RecommendViewModel : ViewModel() {

    private val _tuijianGedan = MutableLiveData<TuijianGedanBean>()
    val tuijianGedan: LiveData<TuijianGedanBean> get() = _tuijianGedan

    private val _banner = MutableLiveData<Banner>()
    val banner: LiveData<Banner> get() = _banner

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
}
