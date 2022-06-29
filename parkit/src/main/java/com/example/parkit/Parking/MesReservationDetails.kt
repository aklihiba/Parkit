package com.example.parkit.Parking

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.parkit.R
import com.example.parkit.database.AppDatabase

class MesReservationDetails : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mes_reservation_details, container, false)
    }

//
//    val db = AppDatabase.buildDatabase(requireContext());
//    val reservations = binding.reservations as TextView
//    val reservation = db?.getReservationDao()?.getAllReservations().toString()
//    reservations.text = reservation?.toString()
}