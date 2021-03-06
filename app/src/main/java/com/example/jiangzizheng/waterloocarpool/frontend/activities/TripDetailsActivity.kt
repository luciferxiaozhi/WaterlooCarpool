package com.example.jiangzizheng.waterloocarpool.frontend.activities

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.jiangzizheng.waterloocarpool.R
import kotlinx.android.synthetic.main.activity_trip_details.*

class TripDetailsActivity : AppCompatActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trip_details)

        intent?.let {
            val tripDetail = intent.extras?.getParcelable("DETAIL") as TripDetail
            departureAddress.text = tripDetail.departureAddress
            arrivalAddress.text = tripDetail.arrivalAddress
            contactInformation.text = tripDetail.phoneNumber
            priceInformation.text = tripDetail.price.toString()
        }

        confirm_pick_up.setOnClickListener {
            Toast.makeText(this, "Appointment Successful! Please check it in your trips!", Toast.LENGTH_LONG).show()
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

}
