package com.example.network.api


import com.example.network.Bean.Banner
import com.example.network.Bean.FashionlistBean
import com.example.network.Bean.HotlistBean
import com.example.network.Bean.ListsData
import com.example.network.Bean.NewSongs
import com.example.network.Bean.RecommendlistBean
import com.example.network.Bean.Songs
import com.example.network.Bean.lyric
import com.example.network.Bean.songsurl
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("/personalized")
    fun getRecommendlist(@Query("limit") limit: Int): Observable<RecommendlistBean>
    @GET("/top/playlist")
    fun getHotlist(@Query("limit") limit: Int): Observable<HotlistBean>
    @GET("/banner?type=1")
    fun getBanner(): Observable<Banner>
    @GET("/personalized/newsong")
    fun getnewSongs(): Observable<NewSongs>
    @GET("/playlist/track/all")
    fun getSongs(
            @Query("id") id: Long
        ): Observable<Songs>
    @GET("/song/url")
    fun geturl(
        @Query("id") id: Long
    ):Observable<songsurl>

    @GET("/toplist")
    fun gettoplist():Observable<ListsData>


    @GET("/lyric")
    fun getlyric(@Query("id")id:Long):Observable<lyric>
    @GET("/style/playlist")
    fun getFashionlist(@Query("tagId")tagId:Long):Observable<FashionlistBean>
    }
