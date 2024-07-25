package com.example.module.search.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.module.search.bean.Search
import com.example.module.search.bean.Song
import com.example.module.search.network.SearchSong
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object SongRepository {
    private const val BASE_URL = "http://82.156.18.110:3000"

    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .build()

    fun SearchSongs(keywords: String, _songData: MutableLiveData<List<Song>>) {
        Log.d("fas","keyword:"+keywords)
        val service = retrofit.create(SearchSong::class.java)
        service.getSongInfo(keywords)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<Search> {
                override fun onSubscribe(d: Disposable) {
                    // Log.d("fas", "开始连接搜索")
                }

                override fun onError(e: Throwable) {
                    e.printStackTrace()
                    Log.e("NetworkError", "网络错误", e)
                }

                override fun onComplete() {
                    // Log.d("fas", "完成搜索")
                }

                override fun onNext(t: Search) {
                    t.result?.let { result ->
                        result.songs?.let { songs ->
                            _songData.postValue(songs)
                        } ?: run {
                            Log.e("DataError", "歌曲列表为空")
                            _songData.postValue(emptyList()) // 或者其他处理方式
                        }
                    } ?: run {
                        Log.e("DataError", "结果为空")
                        _songData.postValue(emptyList()) // 或者其他处理方式
                    }
                }
            })
    }
    fun getMoreSongs(keywords: String,page:Int, _songData: MutableLiveData<List<Song>>){
        val service = retrofit.create(SearchSong::class.java)
        service.getmoreSongs(keywords,(page-1)*30)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<Search> {
                override fun onSubscribe(d: Disposable) {
                    // Log.d("fas", "开始连接搜索")
                }

                override fun onError(e: Throwable) {
                    e.printStackTrace()
                    Log.e("NetworkError", "网络错误", e)
                }

                override fun onComplete() {
                    // Log.d("fas", "完成搜索")
                }

                override fun onNext(t: Search) {
                    t.result?.let { result ->
                        result.songs?.let { songs ->
                            val currentsongs=_songData.value?: emptyList()
                            val updatesongs=currentsongs+songs
                            _songData.postValue(updatesongs)
                        } ?: run {
                            Log.e("DataError", "歌曲列表为空")
                            _songData.postValue(emptyList()) // 或者其他处理方式
                        }
                    } ?: run {
                        Log.e("DataError", "结果为空")
                        _songData.postValue(emptyList()) // 或者其他处理方式
                    }
                }
            })
    }

}
