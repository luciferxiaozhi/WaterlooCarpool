package com.example.jiangzizheng.waterloocarpool.frontend.activities

import android.os.Parcel
import android.os.Parcelable

data class MyTrips (val dt: String, val dLoc: String, val aLoc: String, val price: Double, val contact: String) :
    Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readDouble(),
        parcel.readString()!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(dt)
        parcel.writeString(dLoc)
        parcel.writeString(aLoc)
        parcel.writeDouble(price)
        parcel.writeString(contact)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MyTrips> {
        override fun createFromParcel(parcel: Parcel): MyTrips {
            return MyTrips(parcel)
        }

        override fun newArray(size: Int): Array<MyTrips?> {
            return arrayOfNulls(size)
        }
    }

}
