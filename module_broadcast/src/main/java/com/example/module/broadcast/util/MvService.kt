package com.example.module.broadcast.util

import com.example.module.broadcast.bean.MvUrl
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface MvService {
    @GET("/mv/url")
    fun getSongurl(@Query("id")id:Long): Observable<MvUrl>
}