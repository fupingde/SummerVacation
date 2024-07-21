package com.example.module.broadcast.util

import com.example.module.broadcast.bean.MvData
import com.example.module.broadcast.bean.MvUrl
import com.example.module.broadcast.bean.OtherMvid
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface MvService {
    @GET("/mv/url")
    fun getSongurl(@Query("id")id:Long): Observable<MvUrl>

    @GET("/mv/detail")
        fun getMvdata(@Query("mvid")mvid:Long):Observable<MvData>
    @GET("/simi/mv")
        fun getOtherMvid(@Query("mvid")mvid:Long):Observable<OtherMvid>


}