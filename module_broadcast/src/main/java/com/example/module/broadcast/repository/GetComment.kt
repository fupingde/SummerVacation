package com.example.module.broadcast.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.module.broadcast.bean.Comments
import com.example.module.broadcast.bean.Onecomment
import com.example.module.broadcast.util.MvService
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object GetComment {
    private const val BASE_URL = "http://82.156.18.110:3000"

    //把retrofit对象和apiService对象的构造先提取出来
    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .build()

    fun getComment(id: Long, _commentdata: MutableLiveData<List<Onecomment>>) {
        val service = retrofit.create(MvService::class.java)
        service.getComments(id).subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : io.reactivex.rxjava3.core.Observer<Comments> {
                override fun onSubscribe(d: Disposable) {
                    Log.d("getcomment", "开始连接搜索")
                }

                override fun onError(e: Throwable) {
                    e.printStackTrace();
                    Log.e("getcomment", "网络错误", e); }

                override fun onComplete() {


                }

                override fun onNext(t: Comments) {
                    Log.d("getcomment", t.toString())
                    _commentdata.postValue(t.comments)

                }
            })


    }
}