package com.example.lgs_module.network

import com.example.lgs_module.bean.Send
import retrofit2.http.GET
import retrofit2.http.Query

interface ifsendCode {
    @GET("/captcha/verify")
    fun send( @Query("phone")phone:String,@Query("captcha")captcha:String ): io.reactivex.rxjava3.core.Observable<Send>
}