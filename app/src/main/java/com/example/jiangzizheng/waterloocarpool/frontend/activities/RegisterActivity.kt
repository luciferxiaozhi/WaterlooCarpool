package com.example.jiangzizheng.waterloocarpool.frontend.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.jiangzizheng.waterloocarpool.R
import com.example.jiangzizheng.waterloocarpool.backend.UserInfo
import com.example.jiangzizheng.waterloocarpool.backend.api.Auth
import com.example.jiangzizheng.waterloocarpool.backend.api.Store
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        confirm_register.setOnClickListener {
            val user = Auth.instance.currentUser ?: throw Error()

            UserInfo.sFirstName = first_name.text.toString()

            Store.UserCollection.update(user.uid, mapOf(
                "firstName" to first_name.text.toString(),
                "lastName" to last_name.text.toString()
            ))
                ?.addOnSuccessListener {
                    startActivity(Intent(this, MainActivity::class.java))
                }
                ?.addOnFailureListener {
                    Toast.makeText(this, "Store user data failed!", Toast.LENGTH_LONG).show()
                    finish()
                }
        }
    }
}