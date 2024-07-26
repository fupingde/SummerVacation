package com.example.module.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.example.module.main.R

@Route(path = "/main/ARouterActivity")
class ARouterActivity : AppCompatActivity() {
    @Autowired(name = "songId")
    @JvmField
    var songId: Long = 0

    @Autowired(name = "songName")
    @JvmField
     var songName: String= null.toString()

    @Autowired(name = "artistName")
    @JvmField
     var artistName: String= null.toString()

    @Autowired(name = "songImageUrl")
    @JvmField
    var songImageUrl: String= null.toString()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_arouter)

        // 注入依赖
        ARouter.getInstance().inject(this)
             initView()

        }

    private fun initView() {
        // 确保所有必需的参数都已经被注入

            val intent = Intent(this, MusicPlayActivity::class.java).apply {
                putExtra("SONG_ID", songId)
                putExtra("SONG_NAME", songName)
                putExtra("SONG_ARTIST", artistName)
                putExtra("SONG_PICTUREURL", songImageUrl)
            }
            startActivity(intent)
    }
}
