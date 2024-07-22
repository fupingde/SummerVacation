package com.example.Network.api;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u000e\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\'J\u0018\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00060\u00032\b\b\u0001\u0010\u0007\u001a\u00020\bH\'J,\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\u00032\b\b\u0001\u0010\u000b\u001a\u00020\f2\b\b\u0001\u0010\u0007\u001a\u00020\b2\b\b\u0001\u0010\r\u001a\u00020\bH\'J\u0018\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000f0\u00032\b\b\u0001\u0010\u0007\u001a\u00020\bH\'J\u000e\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00110\u0003H\'J\u0018\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00130\u00032\b\b\u0001\u0010\u000b\u001a\u00020\fH\'\u00a8\u0006\u0014"}, d2 = {"Lcom/example/Network/api/ApiService;", "", "getBanner", "Lio/reactivex/Observable;", "Lcom/example/Network/Bean/Banner;", "getRemenGedan", "Lcom/example/Network/Bean/ReMenGeDanBean;", "limit", "", "getSongs", "Lcom/example/Network/Bean/Songs;", "id", "", "offset", "getTuijianGedan", "Lcom/example/Network/Bean/TuijianGedanBean;", "getnewSongs", "Lcom/example/Network/Bean/NewSongs;", "geturl", "Lcom/example/Network/Bean/songsurl;", "module_main_debug"})
public abstract interface ApiService {
    
    @retrofit2.http.GET(value = "/personalized")
    @org.jetbrains.annotations.NotNull
    public abstract io.reactivex.Observable<com.example.Network.Bean.TuijianGedanBean> getTuijianGedan(@retrofit2.http.Query(value = "limit")
    int limit);
    
    @retrofit2.http.GET(value = "/top/playlist")
    @org.jetbrains.annotations.NotNull
    public abstract io.reactivex.Observable<com.example.Network.Bean.ReMenGeDanBean> getRemenGedan(@retrofit2.http.Query(value = "limit")
    int limit);
    
    @retrofit2.http.GET(value = "/dj/banner")
    @org.jetbrains.annotations.NotNull
    public abstract io.reactivex.Observable<com.example.Network.Bean.Banner> getBanner();
    
    @retrofit2.http.GET(value = "/personalized/newsong")
    @org.jetbrains.annotations.NotNull
    public abstract io.reactivex.Observable<com.example.Network.Bean.NewSongs> getnewSongs();
    
    @retrofit2.http.GET(value = "playlist/track/all")
    @org.jetbrains.annotations.NotNull
    public abstract io.reactivex.Observable<com.example.Network.Bean.Songs> getSongs(@retrofit2.http.Query(value = "id")
    long id, @retrofit2.http.Query(value = "limit")
    int limit, @retrofit2.http.Query(value = "offset")
    int offset);
    
    @retrofit2.http.GET(value = "/song/url")
    @org.jetbrains.annotations.NotNull
    public abstract io.reactivex.Observable<com.example.Network.Bean.songsurl> geturl(@retrofit2.http.Query(value = "id")
    long id);
}