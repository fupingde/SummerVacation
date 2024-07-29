package com.example.lgs_module.login

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.example.lgs_module.bean.Regitser
import com.example.lgs_module.bean.Send
import com.example.lgs_module.bean.Visitor
import com.example.lgs_module.fragment.PasswordFragment
import com.example.lgs_module.login.databinding.ActivityLoginBinding
import com.example.lgs_module.repository.IfSendRepository
import com.example.lgs_module.repository.LoginRepository
import com.example.lgs_module.repository.SendRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

@Route(path = "/login/LoginActivity")
class LoginActivity : AppCompatActivity() {

    val mbinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    var imageurl:String= null.toString()
    var userName:String= null.toString()
    var date = false
    var judgeDate = false
    var id: Long = 0
    var userid: Long = 0
    val swich by lazy { mbinding.switc }
    val resend by lazy { mbinding.resend }
    private var countDownTimer: CountDownTimer? = null
    val login by lazy { mbinding.login }
    val startRegitser by lazy { mbinding.startRegist }
    val sendButton by lazy { mbinding.resend }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(mbinding.root)
//        ARouter.getInstance().inject(this)
        initView()
        initclick()

    }

    private fun initView() {
        val sharedPreferences = getSharedPreferences("logindata", Context.MODE_PRIVATE)
       val usloginid = sharedPreferences.getLong("loginid", 0)
       if (usloginid.toInt() !=0){
           ARouter.getInstance().build("/main/MainActivity")
               .navigation()
           finish()
       }
    }

    private fun initclick() {
        swich.setOnClickListener {
            // 隐藏初始布局
            mbinding.initialLayout.visibility = View.GONE
            // 显示Fragment容器
            mbinding.fragmentContainer.visibility = View.VISIBLE
            val fragment = PasswordFragment()
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)  // Optional: add to back stack to allow user to navigate back
                .commit()
        }
        supportFragmentManager.addOnBackStackChangedListener {
            if (supportFragmentManager.backStackEntryCount == 0) {
                // 显示初始布局
                mbinding.initialLayout.visibility = View.VISIBLE
                mbinding.fragmentContainer.visibility = View.GONE
            }
        }
        resend.setOnClickListener {
            sendMessage()


        }

        login.setOnClickListener {
            judgment()


        }

        startRegitser.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)


        }
        mbinding.vister.setOnClickListener {
            LoginRepository.getApiService().visit_login().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<Visitor> {
                    override fun onSubscribe(d: Disposable) {
                        Log.d("fas", "开始游客登录")
                    }

                    override fun onError(e: Throwable) {
                        Log.d("fas", e.stackTrace.toString())
                    }

                    override fun onComplete() {
                        if (userid.toInt() != 0) {
                            Toast.makeText(this@LoginActivity, "登录成功", Toast.LENGTH_SHORT)
                                .show()
                            val editor = getSharedPreferences("visitdata", Context.MODE_PRIVATE).edit()
                            editor.putLong("visitid",userid)
                            editor.apply()
                            ARouter.getInstance().build("/main/MainActivity")
                                .navigation()
                            finish()
                        }
                        Log.d("fas", "完成登录")
                    }

                    override fun onNext(t: Visitor) {
                        userid = t.userId


                        Log.d("fas", t.toString())
                    }


                })

        }
    }

    private fun sendMessage() {
        SendRepository.getApiService().send(mbinding.nphone.text.toString())
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<Send> {
                override fun onSubscribe(d: Disposable) {
                    Log.d("fas", "开始连接发送信息")
                }

                override fun onError(e: Throwable) {
                    // Toast.makeText(this@RegisterActivity,"发送失败",Toast.LENGTH_SHORT).show()
                    Log.d("fas", e.stackTrace.toString())
                    Log.d("fas", "发送验证请求失败1")
                }

                override fun onComplete() {
                    Log.d("fas", "连接完成")
                    if (date) {
                        Toast.makeText(this@LoginActivity, "已发送", Toast.LENGTH_SHORT).show()
                        startCountDown();
                    } else {
                        Toast.makeText(this@LoginActivity, "发送失败", Toast.LENGTH_SHORT).show()

                    }

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
             countDownTimer = object : CountDownTimer(60000, 1000) {
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
            countDownTimer?.start()
        }
    }

    fun judgment() {
        IfSendRepository.getApiService()
            .send(mbinding.nphone.text.toString(), mbinding.captcha.text.toString())
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : io.reactivex.rxjava3.core.Observer<Send> {
                override fun onSubscribe(d: Disposable) {
                    Log.d("fas", "开始连接验证信息")
                }

                override fun onError(e: Throwable) {
                    //  Toast.makeText(this@RegisterActivity,"发送失败",Toast.LENGTH_SHORT).show()
                    Log.d("fas", "验证请求失败")
                }

                override fun onComplete() {
                    if (!judgeDate) {
                        Toast.makeText(this@LoginActivity, "验证码错误", Toast.LENGTH_SHORT).show()
                    }
                    Log.d("fas", "连接完成")
                }

                override fun onNext(t: Send) {
                    Log.d("fas", t.toString())
                    val body = t
                    judgeDate = body.data
                    if (body.data) {
                        logining()
                    }

                }


            })

    }

    fun logining() {
        LoginRepository.getApiService()
            .cap_Login(mbinding.nphone.text.toString(), mbinding.captcha.text.toString())
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<Regitser> {
                override fun onSubscribe(d: Disposable) {
                    Log.d("fas", "开始连接登录信息")

                }

                override fun onError(e: Throwable) {
                    e.printStackTrace()
                    Log.d("fas", "e" + e.toString())
                }

                override fun onComplete() {
                    if (id.toInt() != 0) {
                        val editor = getSharedPreferences("logindata", Context.MODE_PRIVATE).edit()
                        editor.putLong("loginid",id)
                        editor.putString("imageurl",imageurl)
                        editor.putString("usename",userName)
                        editor.apply()
                        Toast.makeText(this@LoginActivity, "登录成功", Toast.LENGTH_SHORT).show()
                        ARouter.getInstance().build("/main/MainActivity")
                            .navigation()
                        finish()
                    } else {
                        Toast.makeText(this@LoginActivity, "登录失败，请重试", Toast.LENGTH_SHORT)
                            .show()

                    }

                    Log.d("fas", "连接完成")
                }

                override fun onNext(t: Regitser) {
                    id = t.account.id
                    imageurl=t.profile.avatarUrl
                    userName=t.account.userName
                    Log.d("fas", "登录完成，id为"+id)

                }


            })

    }

    override fun onDestroy() {
        super.onDestroy()
        countDownTimer?.cancel()
    }
}


