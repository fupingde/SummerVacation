package com.example.lgs_module.repository

import com.example.lgs_module.network.ifsendCode
import com.example.lgs_module.network.registerService
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object RegesterRepository {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://1258656679-dk116gec67-gz.scf.tencentcs.com")
        .addConverterFactory(GsonConverterFactory.create())//这里添加GSON的converter,后面把数据解析成对象要用。
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .build()

    private val apiService = retrofit.create(registerService::class.java)

    fun getApiService(): registerService {
        return apiService
    }
}