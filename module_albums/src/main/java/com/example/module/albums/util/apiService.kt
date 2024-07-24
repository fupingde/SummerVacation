package com.example.module.albums.util

import com.example.module.albums.bean.AlubumsList
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface apiService {
    @GET("/album")
    fun getSongInfo(@Query("id") id:Long): Observable<AlubumsList>
}