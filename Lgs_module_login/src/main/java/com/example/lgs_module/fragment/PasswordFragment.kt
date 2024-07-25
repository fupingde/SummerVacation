package com.example.lgs_module.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.launcher.ARouter
import com.example.lgs_module.bean.Regitser
import com.example.lgs_module.login.databinding.FragmentPwloginBinding
import com.example.lgs_module.repository.LoginRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.HttpException
import java.io.IOException

class PasswordFragment : Fragment() {

    private var _vbinding :FragmentPwloginBinding ?=null
    private val vbinding get() = _vbinding!!
    private var id: Long = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _vbinding=FragmentPwloginBinding.inflate(inflater, container, false)
        return vbinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vbinding.back.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
        vbinding.passlogin.setOnClickListener {
            login()
        }
    }


    private fun login() {
        LoginRepository.getApiService().pw_Login(vbinding.phonenm.text.toString(), vbinding.password.text.toString())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<Regitser> {
                override fun onSubscribe(d: Disposable) {
                    Log.d("fas", "开始连接验证信息")
                }

                override fun onError(e: Throwable) {
                    handleLoginError(e)
                }

                override fun onComplete() {
                    ARouter.getInstance().build("/main/MainActivity").navigation()
                    Toast.makeText(context, "登录成功", Toast.LENGTH_SHORT).show()
                    Log.d("fas", "连接完成")
                }

                override fun onNext(t: Regitser) {
                    id = t.account.id
                }
            })
    }

    private fun handleLoginError(e: Throwable) {
        when (e) {
            is HttpException -> {
                // Handle HTTP errors
                Log.d("fas", "HTTP error: ${e.message()}")
                Toast.makeText(context, "登录失败: ${e.message()}", Toast.LENGTH_SHORT).show()
            }
            is IOException -> {
                // Handle network errors
                Log.d("fas", "Network error: ${e.message}")
                Toast.makeText(context, "网络错误，请检查您的连接", Toast.LENGTH_SHORT).show()
            }
            else -> {
                // Handle other errors
                Log.d("fas", "Unknown error: ${e.message}")
                Toast.makeText(context, "未知错误发生", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        _vbinding=null
        super.onDestroyView()
    }
}
