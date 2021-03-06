package com.example.parkit.Parking

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.navigation.findNavController
import com.example.parkit.R
import com.example.parkit.database.AppDatabase
import com.example.parkit.databinding.FragmentReservationDetailsBinding
import com.example.parkit.entity.Reservation
import java.time.LocalDateTime


class ReservationDetails : Fragment() {

    private lateinit var binding: FragmentReservationDetailsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentReservationDetailsBinding.inflate(layoutInflater)
        val view = binding.root

        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val pref by lazy { requireActivity().getSharedPreferences("parkitData", AppCompatActivity.MODE_PRIVATE) }
        super.onViewCreated(view, savedInstanceState)

        val con = pref.getBoolean("connected", false)
        if (!con)  {
          view.findNavController().navigate(R.id.action_reservationDetails_to_connexionFragment2)

        }

        binding.deconnect.setOnClickListener{
           pref.edit(){
               putBoolean("connected",false)
               apply()
           }

            it.findNavController().navigate(R.id.action_reservationDetails_to_fragmentParkings)

        }

        val db = AppDatabase.buildDatabase(requireContext());
        val reservations = binding.reservations as TextView
        val reservation = db?.getReservationDao()?.getAllReservations().toString()
        reservations.text = reservation?.toString()

    }
}