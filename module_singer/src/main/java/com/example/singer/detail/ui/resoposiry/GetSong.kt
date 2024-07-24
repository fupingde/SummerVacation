package com.example.singer.detail.ui.resoposiry

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.singer.detail.ui.bean.ArtistX
import com.example.singer.detail.ui.bean.HotSongsResponse
import com.example.singer.detail.ui.bean.Song
import com.example.singer.detail.ui.util.service
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object GetSong {
    private const val BASE_URL = "http://82.156.18.110:3000"

    //把retrofit对象和apiService对象的构造先提取出来
    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .build()

    fun SearchSongs(id:Long, _songData: MutableLiveData<List<Song>>,_imageData: MutableLiveData<List<ArtistX>>) {
        Log.d("fas","id:"+id)
        val service = retrofit.create(service::class.java)
        service.getSongInfo(id).subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())//在安卓主线程（执行onNext的逻辑）
            .subscribe(object : Observer<HotSongsResponse> {
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

                override fun onNext(t: HotSongsResponse) {
                    // _songData.postValue(t)
                        Log.d("moduleSingerdata", t.toString())
                   _imageData.postValue(listOf(t.artist))
                    _songData.postValue(t.hotSongs)
                }

            })
    }


}