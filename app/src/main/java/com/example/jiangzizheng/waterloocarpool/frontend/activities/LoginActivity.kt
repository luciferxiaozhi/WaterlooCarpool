package com.example.jiangzizheng.waterloocarpool.frontend.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.jiangzizheng.waterloocarpool.R
import kotlinx.android.synthetic.main.activity_login.*

/**
 * A login screen that offers login via email/google.
 */
class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        email_sign_in_button.setOnClickListener {
            startActivity(Intent(this, EmailLoginActivity::class.java))
        }

        google_sign_in_button.setOnClickListener {
            startActivity(Intent(this, GoogleLoginActivity::class.java))
        }
    }
}
