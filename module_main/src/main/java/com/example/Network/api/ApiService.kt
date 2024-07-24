package com.example.Network.api


import com.example.Network.Bean.Banner
import com.example.Network.Bean.ListsData
import com.example.Network.Bean.NewSongs
import com.example.Network.Bean.ReMenGeDanBean
import com.example.Network.Bean.Songs
import com.example.Network.Bean.TuijianGedanBean
import com.example.Network.Bean.liuxinggedan
import com.example.Network.Bean.lyric
import com.example.Network.Bean.songsurl
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("/personalized")
    fun getTuijianGedan(@Query("limit") limit: Int): Observable<TuijianGedanBean>
    @GET("/top/playlist")
    fun getRemenGedan(@Query("limit") limit: Int): Observable<ReMenGeDanBean>
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
    fun getLiuXingGeDan(@Query("tagId")tagId:Long):Observable<liuxinggedan>
    }
