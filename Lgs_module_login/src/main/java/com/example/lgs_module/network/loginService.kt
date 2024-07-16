package com.example.lgs_module.network

import com.example.lgs_module.bean.Regitser
import com.example.lgs_module.bean.Visitor
import retrofit2.http.GET
import retrofit2.http.Query

interface loginService {
    @GET("/login/cellphone")
    fun pw_Login(@Query("phone")phone:String, @Query("password")password:String):io.reactivex.rxjava3.core.Observable<Regitser>
    @GET("/login/cellphone")
    fun cap_Login(@Query("phone")phone:String, @Query("captcha")captcha:String):io.reactivex.rxjava3.core.Observable<Regitser>
    @GET("/register/anonimous")
    fun visit_login():io.reactivex.rxjava3.core.Observable<Visitor>
}