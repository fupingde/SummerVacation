package com.example.module.search.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.module.search.bean.singer.Artist
import com.example.module.search.bean.singer.ResponseData
import com.example.module.search.network.SearchSinger
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object SingerRepository {
    private const val BASE_URL = "http://82.156.18.110:3000"

    //把retrofit对象和apiService对象的构造先提取出来
    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .build()

    fun SearchSongs(keywords: String, _singerData: MutableLiveData<List<Artist>>) {
        val service = retrofit.create(SearchSinger::class.java)
        service.getSongInfo(keywords).subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())//在安卓主线程（执行onNext的逻辑）
            .subscribe(object : Observer<ResponseData> {
                override fun onSubscribe(d: Disposable) {
                }

                override fun onError(e: Throwable) {
                    e.printStackTrace();
                    Log.e("NetworkError", "网络错误", e); // 使用日志记录错误
                }

                override fun onComplete() {

                }

                override fun onNext(t: ResponseData) {
                    // _songData.postValue(t)
                    Log.d("Singer.Value",t.toString())
                    t?.let {
                    _singerData.postValue(t.data.list)}
                }

            })
    }





}