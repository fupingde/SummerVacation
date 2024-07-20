package com.example.lgs_module.login

import android.annotation.SuppressLint
import android.database.Observable
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.lgs_module.bean.Regitser
import com.example.lgs_module.bean.Send
import com.example.lgs_module.login.databinding.ActivityRegisterBinding
import com.example.lgs_module.repository.IfSendRepository
import com.example.lgs_module.repository.RegesterRepository
import com.example.lgs_module.repository.SendRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlin.properties.Delegates

class RegisterActivity : AppCompatActivity() {

    val mbinding by lazy { ActivityRegisterBinding.inflate(layoutInflater) }
    val sendButton by lazy { mbinding.send }
    val registerButton by lazy { mbinding.regist }
     var id:Long=0
    var date:Boolean=false;
    var judgeDate:Boolean=false;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(mbinding.root)
        initclick()



    }

    private fun initclick() {
        sendButton.setOnClickListener {
           var phonenumber=mbinding.phonen.text.toString()
            Log.d("fas",phonenumber)
         sendMessage(phonenumber)


        }
        registerButton.setOnClickListener {
         judgment()
          //register()

           }

    }

       fun judgment() {
        IfSendRepository.getApiService().send(mbinding.phonen.text.toString(),mbinding.captcha.text.toString()).subscribeOn(Schedulers.io()).subscribeOn(AndroidSchedulers.mainThread()).subscribe(object:io.reactivex.rxjava3.core.Observer<Send>{
            override fun onSubscribe(d: Disposable) {
                Log.d("fas","开始连接验证信息")
            }

            override fun onError(e: Throwable) {
              //  Toast.makeText(this@RegisterActivity,"发送失败",Toast.LENGTH_SHORT).show()
                Log.d("fas","验证请求失败")
            }

            override fun onComplete() {
                if (judgeDate&& id.toInt() ==0){
                    Toast.makeText(this@RegisterActivity,"已经注册，请登录",Toast.LENGTH_SHORT).show()

                }
                else if(judgeDate&& id.toInt() !=0){
                    Toast.makeText(this@RegisterActivity,"注册成功，开始登录",Toast.LENGTH_SHORT).show()

                }
            Log.d("fas","连接完成")
            }

            override fun onNext(t: Send) {
                Log.d("fas",t.toString())
                val body=t
                judgeDate=body.data
                if(body.data){
                    register()
                }

            }


        } )

    }

    private fun register() {
       RegesterRepository.getApiService().Register(mbinding.phonen.text.toString(),mbinding.password.text.toString(),mbinding.captcha.text.toString(),mbinding.nickName.text.toString())
           .subscribeOn(Schedulers.io()).subscribeOn(AndroidSchedulers.mainThread()).subscribe(object :io.reactivex.rxjava3.core.Observer<Regitser>{
               override fun onSubscribe(d: Disposable) {
                   Log.d("fas","开始连接发送注册")
               }

               override fun onError(e: Throwable) {
                   //Toast.makeText(this@RegisterActivity,"注册失败",Toast.LENGTH_SHORT).show()
                   Log.d("fas","注册请求失败")
               }

               override fun onComplete() {
                   Log.d("fas","注册完成")

               }

               override fun onNext(t: Regitser) {
               Log.d("fas",t.toString())
                   var body=t;
                id=  body.account.id
               }


           })
    }

    fun sendMessage( phoneNumber:String){
     SendRepository.getApiService().send(phoneNumber).subscribeOn(Schedulers.io()).subscribeOn(
         AndroidSchedulers.mainThread()).subscribe (object :io.reactivex.rxjava3.core.Observer<Send>{
         override fun onSubscribe(d: Disposable) {
             Log.d("fas","开始连接发送信息")
         }

         override fun onError(e: Throwable) {
           // Toast.makeText(this@RegisterActivity,"发送失败",Toast.LENGTH_SHORT).show()
            Log.d ("fas", e.stackTrace.toString())
             Log.d("fas","发送验证请求失败1")
         }

         override fun onComplete() {
             if(date) {
                 Toast.makeText(this@RegisterActivity,"已发送",Toast.LENGTH_SHORT).show()
                 startCountDown();
             }
             else{
                 Toast.makeText(this@RegisterActivity,"发送失败",Toast.LENGTH_SHORT).show()

             }
          Log.d("fas","连接完成")


         }

         override fun onNext(t: Send) {
            date=t.data
             Log.d("fas",t.toString())

         }


     })




    }

    private fun startCountDown() {
        sendButton.isEnabled=false
       val countDownTimer = object : CountDownTimer(60000, 1000) {
           override fun onTick(millisUntilFinished: Long) {
              sendButton.text = "倒计时: ${millisUntilFinished / 1000}秒"
               // 修改按钮背景颜色示例
               sendButton.setBackgroundColor(Color.BLUE)
           }



           override fun onFinish() {
               sendButton.text = "发送"
               sendButton.isEnabled = true
               // 恢复按钮背景颜色示例
               sendButton.setBackgroundColor(Color.WHITE)
           }
       }
   }
}