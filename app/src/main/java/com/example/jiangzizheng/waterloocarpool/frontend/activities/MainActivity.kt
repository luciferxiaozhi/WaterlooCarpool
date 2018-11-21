package com.example.jiangzizheng.waterloocarpool.frontend.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.ActionBarDrawerToggle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.example.jiangzizheng.waterloocarpool.R
import com.example.jiangzizheng.waterloocarpool.backend.UserInfo
import com.example.jiangzizheng.waterloocarpool.backend.UserInfo.sFirstName
import com.example.jiangzizheng.waterloocarpool.backend.api.Auth
import com.example.jiangzizheng.waterloocarpool.backend.api.Store
import com.example.jiangzizheng.waterloocarpool.frontend.fragments.DriverFragment
import com.example.jiangzizheng.waterloocarpool.frontend.fragments.PassengerFragment
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*

class MainActivity : AppCompatActivity() {
    private val mainHandler = Handler(Looper.getMainLooper())

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

    private val mOnDrawerItemSelectedListener = NavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.nav_accountInfo -> mainHandler.postDelayed({
                startActivity(Intent(this, AccountInfoActivity::class.java))
            }, 300L)

            R.id.nav_exit -> mainHandler.postDelayed({
                Auth.disposeOAuthToken(this)
                Auth.instance.signOut()
                startActivity(Intent(this, LoginActivity::class.java))
            }, 300L)
        }
        drawer_layout.closeDrawer(GravityCompat.START)
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(this, drawer_layout, toolbar,
            R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        supportFragmentManager.beginTransaction()
            .replace(R.id.layout_container, DriverFragment.newInstance())
            .commit()

        val user = Auth.instance.currentUser
        if (user != null) {
            Store.UserCollection.fetch(user.uid)
                ?.addOnSuccessListener {
                    UserInfo.sFirstName = it.firstName
                }
        }

        nav_view.setNavigationItemSelectedListener(mOnDrawerItemSelectedListener)
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

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}
