package com.example.singer.detail.ui.util

import com.example.singer.detail.ui.bean.HotSongsResponse
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface service {
    @GET("/artists")
    fun getSongInfo(@Query("id") id:Long): Observable<HotSongsResponse>
}