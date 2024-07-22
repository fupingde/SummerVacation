package com.example.lgs_module.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.lgs_module.bean.Regitser
import com.example.lgs_module.login.R
import com.example.lgs_module.login.databinding.FragmentPwloginBinding
import com.example.lgs_module.repository.LoginRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class PasswordFragment: Fragment() {
    val vbinding by lazy {
        FragmentPwloginBinding.inflate(layoutInflater)
    }
    var id:Long=0
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

    // val view=inflater.inflate(R.layout.fragment_pwlogin,container,false)
        return vbinding.root
         }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vbinding.back.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
        vbinding.passlogin.setOnClickListener {
            login()

        }
    }

     fun login() {
        LoginRepository.getApiService().cap_Login(vbinding.phonenm .text.toString(),vbinding.password.text.toString()) .subscribeOn(
            Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
        .subscribe(object : Observer<Regitser> {
            override fun onSubscribe(d: Disposable) {
                Log.d("fas", "开始连接验证信息")

            }

            override fun onError(e: Throwable) {
                e.printStackTrace()
                Log.d("fas", "e"+e.toString())
            }

            override fun onComplete() {
                Toast.makeText(context, "登录成功", Toast.LENGTH_SHORT)
                    .show()
                Log.d("fas", "连接完成")
            }

            override fun onNext(t: Regitser) {
                id= t.account.id


            }


        })


    }


}
