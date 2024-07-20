package com.example.module.search.network

import com.example.module.search.bean.DataAlbum
import com.example.module.search.bean.Search
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchSong {
    @GET("/search")
    fun getSongInfo(@Query("keywords") keywords: String): Observable<Search>

    @GET("/search")
    fun getAlbum(  @Query("keywords") keywords: String,
    @Query("type") type:Int):Observable<DataAlbum>
}