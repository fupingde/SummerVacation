package com.example.module.search.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.module.search.bean.Search
import com.example.module.search.bean.Song
import com.example.module.search.bean.mv.Mv
import com.example.module.search.bean.mv.mvidata
import com.example.module.search.network.SearchSong
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object MvRepository {
    private const val BASE_URL = "http://82.156.18.110:3000"

    //把retrofit对象和apiService对象的构造先提取出来
    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .build()
    fun SearchSongs(keywords: String, _songData: MutableLiveData<List<Mv>>) {
        Log.d("fas","keyword:"+keywords)
        val service = SongRepository.retrofit.create(SearchSong::class.java)
        service.getMv(keywords).subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())//在安卓主线程（执行onNext的逻辑）
            .subscribe(object : Observer<mvidata> {
                override fun onSubscribe(d: Disposable) {
                    //  Log.d("fas", "开始连接搜索")
                }

                override fun onError(e: Throwable) {
                    e.printStackTrace();
                    Log.e("NetworkError", "网络错误", e); // 使用日志记录错误
                }

                override fun onComplete() {
                    //    Log.d("fas", "完成搜索")

                }

                override fun onNext(t: mvidata) {
                    // _songData.postValue(t)
                    Log.d("mvid",t.toString())
                    _songData.postValue(t.result.mvs)
                }

            })
    }
}