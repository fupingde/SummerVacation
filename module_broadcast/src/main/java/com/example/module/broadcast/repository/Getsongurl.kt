package com.example.module.broadcast.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.module.broadcast.bean.MvUrl
import com.example.module.broadcast.util.MvService
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object Getsongurl {

    private const val BASE_URL = "http://82.156.18.110:3000"

    //把retrofit对象和apiService对象的构造先提取出来
    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .build()

    fun SearchSongs(id: Long, _songData: MutableLiveData<List<MvUrl.Data>>) {
        Log.d("fas", "id:" + id)
        val service = retrofit.create(MvService::class.java)
        service.getSongurl(id).subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())//在安卓主线程（执行onNext的逻辑）
            .subscribe(object : Observer<MvUrl> {
                override fun onSubscribe(d: Disposable) {
                    //  Log.d("fas", "开始连接搜索")

                }

                override fun onError(e: Throwable) {
                    e.printStackTrace();
                    Log.e("GetsongurlNetworkError", "网络错误", e); // 使用日志记录错误
                }

                override fun onComplete() {
                    //    Log.d("fas", "完成搜索")

                }

                override fun onNext(t: MvUrl) {
                    // _songData.postValue(t)
                    Log.d("getURL", t.data.toString())
                    _songData.postValue(listOf(t.data))
                }

            })
    }


}

