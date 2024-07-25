package com.example.lgs_module.login

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.launcher.ARouter
import com.example.lgs_module.bean.Regitser
import com.example.lgs_module.bean.Send
import com.example.lgs_module.login.databinding.ActivityRegisterBinding
import com.example.lgs_module.repository.IfSendRepository
import com.example.lgs_module.repository.RegesterRepository
import com.example.lgs_module.repository.SendRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class RegisterActivity : AppCompatActivity() {

    private val mbinding by lazy { ActivityRegisterBinding.inflate(layoutInflater) }
    private val sendButton by lazy { mbinding.send }
    private val registerButton by lazy { mbinding.regist }
    private var id: Long = 0
    private var date: Boolean = false
    private var judgeDate: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mbinding.root)
        initclick()
    }

    private fun initclick() {
        sendButton.setOnClickListener {
            val phoneNumber = mbinding.phonen.text.toString()
            Log.d("fas", phoneNumber)
            sendMessage(phoneNumber)
        }
        registerButton.setOnClickListener {
            judgment()
        }
    }

    private fun judgment() {
        IfSendRepository.getApiService().send(mbinding.phonen.text.toString(), mbinding.captcha.text.toString())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<Send> {
                override fun onSubscribe(d: Disposable) {
                    Log.d("fas", "开始连接验证信息")
                }

                override fun onError(e: Throwable) {
                    Log.d("fas", "验证请求失败")
                }

                override fun onComplete() {
                    if (judgeDate && id.toInt() == 0) {
                        Toast.makeText(this@RegisterActivity, "已经注册，请登录", Toast.LENGTH_SHORT).show()
                    } else if (judgeDate && id.toInt() != 0) {
                        Toast.makeText(this@RegisterActivity, "注册成功，开始登录", Toast.LENGTH_SHORT).show()
                        register()
                    }
                    Log.d("fas", "连接完成")
                }

                override fun onNext(t: Send) {
                    Log.d("fas", t.toString())
                    val body = t
                    judgeDate = body.data
                }
            })
    }

    private fun register() {
        RegesterRepository.getApiService().Register(
            mbinding.phonen.text.toString(),
            mbinding.password.text.toString(),
            mbinding.captcha.text.toString(),
            mbinding.nickName.text.toString()
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<Regitser> {
                override fun onSubscribe(d: Disposable) {
                    Log.d("fas", "开始连接发送注册")
                }

                override fun onError(e: Throwable) {
                    Log.d("fas", "注册请求失败")
                }

                override fun onComplete() {
                    Toast.makeText(this@RegisterActivity, "登录成功", Toast.LENGTH_SHORT).show()
                    ARouter.getInstance().build("/main/MainActivity").navigation()
                }

                override fun onNext(t: Regitser) {
                    Log.d("fas", t.toString())
                    val body = t
                    id = body.account.id
                }
            })
    }

    fun sendMessage(phoneNumber: String) {
        SendRepository.getApiService().send(phoneNumber)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<Send> {
                override fun onSubscribe(d: Disposable) {
                    Log.d("fas", "开始连接发送信息")
                }

                override fun onError(e: Throwable) {
                    Log.d("fas", "发送验证请求失败1")
                }

                override fun onComplete() {
                    if (date) {
                        startCountDown()
                    }
                    Log.d("fas", "连接完成")
                }

                override fun onNext(t: Send) {
                    date = t.data
                    Log.d("fas", t.toString())
                }
            })
    }

    private fun startCountDown() {
        runOnUiThread {
            sendButton.isEnabled = false
            val countDownTimer = object : CountDownTimer(60000, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    sendButton.text = "倒计时: ${millisUntilFinished / 1000}秒"
                    sendButton.setBackgroundColor(Color.BLUE)
                }

                override fun onFinish() {
                    sendButton.text = "发送"
                    sendButton.isEnabled = true
                    sendButton.setBackgroundColor(Color.WHITE)
                }
            }
            countDownTimer.start()
        }
    }
}
