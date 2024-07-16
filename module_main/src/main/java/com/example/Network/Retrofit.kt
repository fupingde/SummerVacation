package com.example.Network

import com.example.Network.api.ApiService
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object Retrofit {
    private const val BASE_URL = "https://1258656679-dk116gec67-gz.scf.tencentcs.com/"

    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }
    val apiService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}
