package com.example.Network.ViewModel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.Network.Bean.TuijianGedanBean
import com.example.Network.Retrofit
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class RecommendViewModel : ViewModel() {

    private val _tuijianGedan = MutableLiveData<TuijianGedanBean>()
    val tuijianGedan: LiveData<TuijianGedanBean> get() = _tuijianGedan

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
}
