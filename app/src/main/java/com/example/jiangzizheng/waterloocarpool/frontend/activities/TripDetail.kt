package com.example.jiangzizheng.waterloocarpool.frontend.activities

import android.os.Parcel
import android.os.Parcelable

data class TripDetail(val departureAddress: String, val arrivalAddress: String, val phoneNumber: String, val price: Double) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readDouble()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(departureAddress)
        parcel.writeString(arrivalAddress)
        parcel.writeString(phoneNumber)
        parcel.writeDouble(price)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TripDetail> {
        override fun createFromParcel(parcel: Parcel): TripDetail {
            return TripDetail(parcel)
        }

        override fun newArray(size: Int): Array<TripDetail?> {
            return arrayOfNulls(size)
        }
    }

}