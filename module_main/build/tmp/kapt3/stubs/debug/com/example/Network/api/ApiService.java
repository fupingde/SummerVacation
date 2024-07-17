package com.example.Network.api;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u000e\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\'J\u0018\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00060\u00032\b\b\u0001\u0010\u0007\u001a\u00020\bH\'J\u0018\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\u00032\b\b\u0001\u0010\u0007\u001a\u00020\bH\'\u00a8\u0006\u000b"}, d2 = {"Lcom/example/Network/api/ApiService;", "", "getBanner", "Lio/reactivex/Observable;", "Lcom/example/Network/Bean/Banner;", "getRemenGedan", "Lcom/example/Network/Bean/ReMenGeDanBean;", "limit", "", "getTuijianGedan", "Lcom/example/Network/Bean/TuijianGedanBean;", "module_main_debug"})
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
}