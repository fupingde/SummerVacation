package com.example.lgs_module.network

import android.database.Observable
import android.provider.ContactsContract.CommonDataKinds.Nickname
import com.example.lgs_module.bean.Regitser
import retrofit2.http.GET
import retrofit2.http.Query

interface registerService {
    @GET("/register/cellphone")
    fun Register(@Query("phone")phone:String,@Query("password")password:String,@Query("captcha")captcha:String,@Query("nickname")nickname: String):io.reactivex.rxjava3.core.Observable<Regitser>

}