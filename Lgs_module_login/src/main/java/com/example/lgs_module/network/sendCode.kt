package com.example.lgs_module.network

import android.database.Observable
import com.example.lgs_module.bean.Send

import retrofit2.http.GET
import retrofit2.http.Query

interface sendCode {
        @GET("/captcha/sent")
        fun send( @Query("phone")phone:String ): io.reactivex.rxjava3.core.Observable<Send>

   }