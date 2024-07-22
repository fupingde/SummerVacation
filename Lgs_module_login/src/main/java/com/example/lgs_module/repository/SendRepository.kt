package com.example.lgs_module.repository

import com.example.lgs_module.network.ifsendCode
import com.example.lgs_module.network.sendCode
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object SendRepository {
    private val retrofit = Retrofit.Builder()
        .baseUrl("http://82.156.18.110:3000")
        .addConverterFactory(GsonConverterFactory.create())//这里添加GSON的converter,后面把数据解析成对象要用。
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .build()

    private val apiService = retrofit.create(sendCode::class.java)

    fun getApiService(): sendCode {
        return apiService
    }
}