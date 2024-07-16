package com.example.lgs_moudule_drawerview

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.lgs_moudule_drawerview.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {
    val mbinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val drawerLayout: DrawerLayout by lazy {
        mbinding.main
    }
    private val navView: NavigationView by lazy {
        mbinding.navView
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mbinding.root)
        initView()
        initClick()

    }

    private fun initClick() {
        // 处理导航项点击事件
        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_home -> {

                    // Handle home click
                }

                R.id.nav_MV -> {

                }

                R.id.nav_music -> {

                }

                R.id.nav_collect -> {

                }


                R.id.nav_settings -> {
                    // Handle settings click
                }
                // Add more cases for other menu items
            }
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }
    }

    private fun initView() {
        // 设置ActionBar以支持Drawer Toggle
        setSupportActionBar(mbinding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, mbinding.toolbar,
            R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}
