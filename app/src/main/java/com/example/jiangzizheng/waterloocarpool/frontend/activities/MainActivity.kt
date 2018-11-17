package com.example.jiangzizheng.waterloocarpool.frontend.activities

import android.content.Intent
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import com.example.jiangzizheng.waterloocarpool.R
import com.example.jiangzizheng.waterloocarpool.backend.api.Auth
import com.example.jiangzizheng.waterloocarpool.backend.bean.Trip
import com.example.jiangzizheng.waterloocarpool.backend.bean.User
import com.example.jiangzizheng.waterloocarpool.frontend.fragments.DriverFragment
import com.example.jiangzizheng.waterloocarpool.frontend.fragments.PassengerFragment
import com.google.firebase.Timestamp
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.fragment_driver.*
import kotlinx.android.synthetic.main.fragment_passenger.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_driver -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.layout_container, DriverFragment.newInstance())
                    .commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_passenger -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.layout_container, PassengerFragment.newInstance())
                    .commit()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .replace(R.id.layout_container, DriverFragment.newInstance())
            .commit()

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

    }

    override fun onStart() {
        super.onStart()

        val user = Auth.instance.currentUser
        if (user == null) {
            startActivity(Intent(this, LoginActivity::class.java))
            return
        }
    }
}
