package com.example.jiangzizheng.waterloocarpool.frontend.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.jiangzizheng.waterloocarpool.R


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
