package com.example.module.search.network

import com.example.module.search.bean.singer.ResponseData
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchSinger {
    @GET("/ugc/artist/search")
    fun getSongInfo(@Query("keyword") keyword: String): Observable<ResponseData>
}
