package com.example.jiangzizheng.waterloocarpool.frontend.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.jiangzizheng.waterloocarpool.R
import com.example.jiangzizheng.waterloocarpool.backend.api.Store
import com.example.jiangzizheng.waterloocarpool.backend.bean.Trip
import com.example.jiangzizheng.waterloocarpool.backend.bean.User
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
            val user = User()

            val trip = Trip()

            trip.dCity = driver_departure_city.selectedItem.toString()
            trip.dAddress = departure_address.text.toString()
            trip.aCity = driver_arrival_city.selectedItem.toString()
            trip.aAddress = arrival_address.text.toString()

            trip.dDate = driver_date.text.toString().let { date ->
                Timestamp(SimpleDateFormat("MM/dd hh:mm", Locale.CANADA).parse(date))
            }
            trip.phoneNumber = phone.text.toString()
            trip.vacancies = vacancies.text.toString().toInt()
            trip.price = price.text.toString().toDouble()

            // test
            trip.driver = "k7DO4c9cJGGq7L2bb4JF"

            Store.TripCollection.add(trip)
                ?.addOnSuccessListener {
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
