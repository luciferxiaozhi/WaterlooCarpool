package com.example.jiangzizheng.waterloocarpool.frontend.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.jiangzizheng.waterloocarpool.R
import com.example.jiangzizheng.waterloocarpool.backend.api.Store
import com.example.jiangzizheng.waterloocarpool.backend.bean.Trip
import com.google.firebase.Timestamp
import kotlinx.android.synthetic.main.fragment_passenger.*
import java.text.SimpleDateFormat
import java.util.*


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [PassengerFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [PassengerFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class PassengerFragment : androidx.fragment.app.Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_passenger, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        confirm_passenger.setOnClickListener {
            val trip = Trip()
            trip.departureCity = departure_city.selectedItem.toString()
            trip.arrivalCity = arrival_city.selectedItem.toString()
            trip.dDate = date.text.toString().let { date ->
                Timestamp(SimpleDateFormat("MM/dd", Locale.CANADA).parse(date))
            }
            trip.vacancies = number_of_people.text.toString().toInt()

            Store.TripCollection.search(trip.departureCity, trip.arrivalCity, trip.dDate, trip.vacancies)
                ?.addOnSuccessListener {

                }
                ?.addOnFailureListener {
                    Toast.makeText(activity, "Trip not found!", Toast.LENGTH_LONG).show()
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
         * @return A new instance of fragment PassengerFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() = PassengerFragment()
    }
}
