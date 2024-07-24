package com.example.Network.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.Network.Bean.ListsData
import com.example.Network.Bean.Playlist
import com.example.Network.Retrofit
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class DynamicViewModel : ViewModel() {
    private val _songData: MutableLiveData<List<Playlist>> = MutableLiveData()
    val songData: LiveData<List<Playlist>>
        get() = _songData


    //得到单曲的信息
    fun getSongInfo() {
        Retrofit.apiService.gettoplist().subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())//在安卓主线程（执行onNext的逻辑）
            .subscribe(object : Observer<ListsData> {
                override fun onSubscribe(d: Disposable) {
                    // Log.d("fas", "开始连接搜索")
                }

                override fun onError(e: Throwable) {
                    e.printStackTrace();
                    Log.e("NetworkError", "网络错误", e); // 使用日志记录错误
                }

                override fun onComplete() {
                    Log.d("fas", "List初始化")

                }

                override fun onNext(t: ListsData) {
                    // _songData.postValue(t)
                    // 在循环结束后，将累积的列表发布到 LiveData
                    _songData.postValue(t.list)


                }

            })


    }

}