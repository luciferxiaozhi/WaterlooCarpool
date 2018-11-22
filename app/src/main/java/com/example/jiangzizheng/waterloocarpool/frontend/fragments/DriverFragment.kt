package com.example.jiangzizheng.waterloocarpool.frontend.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.jiangzizheng.waterloocarpool.R
import com.example.jiangzizheng.waterloocarpool.backend.UserInfo
import com.example.jiangzizheng.waterloocarpool.backend.api.Auth
import com.example.jiangzizheng.waterloocarpool.backend.api.Store
import com.example.jiangzizheng.waterloocarpool.backend.bean.Trip
import com.example.jiangzizheng.waterloocarpool.frontend.activities.MyTrips
import com.example.jiangzizheng.waterloocarpool.frontend.activities.MyTripsActivity
import com.google.firebase.Timestamp
import kotlinx.android.synthetic.main.fragment_driver.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [HomeFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class DriverFragment : androidx.fragment.app.Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_driver, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        confirm_driver.setOnClickListener {
            val user = Auth.instance.currentUser ?: throw Error()
            val trip = Trip()

            trip.departureCity = driver_departure_city.selectedItem.toString()
            trip.departureAddress = departure_address.text.toString()
            trip.arrivalCity = driver_arrival_city.selectedItem.toString()
            trip.arrivalAddress = arrival_address.text.toString()

            trip.dDate = driver_date.text.toString().let { date ->
                Timestamp(SimpleDateFormat("MM/dd hh:mm", Locale.CANADA).parse(date))
            }
            trip.phoneNumber = phone.text.toString()
            trip.vacancies = vacancies.text.toString().toInt()
            trip.price = price.text.toString().toDouble()

            trip.driver = user.uid
            trip.driverName = UserInfo.sFirstName

            Store.TripCollection.add(trip)
                ?.addOnSuccessListener {
                    val myTrip = MyTrips(
                        trip.dDate.toDate().toString(),
                        trip.departureAddress + ", " + trip.departureCity,
                        trip.arrivalAddress + ", " + trip.arrivalCity,
                        trip.price,
                        trip.phoneNumber
                    )

                    val intent = Intent(context, MyTripsActivity::class.java)
                    intent.putExtra("TRIPS", myTrip)
                    Toast.makeText(activity, "data added successfully, please wait for your passenger!", Toast.LENGTH_LONG).show()
                }
                ?.addOnFailureListener {e ->
                    Log.w(javaClass.name, "Error writing document", e)
                }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic fun newInstance() = DriverFragment()
    }
}
