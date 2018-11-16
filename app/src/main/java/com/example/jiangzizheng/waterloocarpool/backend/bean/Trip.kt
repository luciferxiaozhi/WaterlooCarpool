package com.example.jiangzizheng.waterloocarpool.backend.bean

import java.sql.Timestamp

data class Trip (
    var dCity: String = "",
    var dAddress: String = "",

    var aCity: String = "",
    var aAddress: String = "",

    var ddate: Timestamp = Timestamp(0),
    var phoneNumber: String = "",
    var vacancies: Int = 0,
    var price: Double = 0.0
)