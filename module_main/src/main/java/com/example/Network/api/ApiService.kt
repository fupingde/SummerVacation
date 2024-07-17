package com.example.Network.api


import com.example.Network.Bean.Banner
import com.example.Network.Bean.ReMenGeDanBean
import com.example.Network.Bean.TuijianGedanBean
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("/personalized")
    fun getTuijianGedan(@Query("limit") limit: Int): Observable<TuijianGedanBean>
    @GET("/top/playlist")
    fun getRemenGedan(@Query("limit") limit: Int): Observable<ReMenGeDanBean>
    @GET("/dj/banner")
    fun getBanner(): Observable<Banner>
}