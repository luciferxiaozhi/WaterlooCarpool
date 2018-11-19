package com.example.jiangzizheng.waterloocarpool.frontend.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.Toolbar
import com.example.jiangzizheng.waterloocarpool.R
import com.example.jiangzizheng.waterloocarpool.backend.api.Store
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore

class AccountInfoActivity : AppCompatActivity() {

    var currUser = FirebaseAuth.getInstance().currentUser
    var db = FirebaseFirestore.getInstance()
    val uid = currUser?.uid.toString()
    val docRef = db.collection("users").document(uid)
    val userEmail = currUser?.email




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_info)

        readDataAndSetText()
    }

    fun readDataAndSetText()
    {
        docRef.get().addOnCompleteListener(this) {task ->
            if (task.isSuccessful)
            {
                val doc = task.getResult()
                val textFName = findViewById<TextView>(R.id.account_info_first_name)
                val textLName = findViewById<TextView>(R.id.account_info_last_name)
                val textEmail = findViewById<TextView>(R.id.account_info_email)

                textFName.setText(doc?.get("firstName").toString())
                textLName.setText(doc?.get("lastName").toString())
                textEmail.setText(userEmail)
            }
        }

    }


}

