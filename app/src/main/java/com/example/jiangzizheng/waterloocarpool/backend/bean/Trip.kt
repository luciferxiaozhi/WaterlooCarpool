package com.example.jiangzizheng.waterloocarpool.backend.bean

import com.google.firebase.Timestamp
import java.util.*

data class Trip (
    var departureCity: String = "",
    var departureAddress: String = "",

    var arrivalCity: String = "",
    var arrivalAddress: String = "",

    var dDate: Timestamp = Timestamp(Date(0)),
    var phoneNumber: String = "",
    var vacancies: Int = 0,
    var price: Double = 0.0,

    var driver: String = ""
)