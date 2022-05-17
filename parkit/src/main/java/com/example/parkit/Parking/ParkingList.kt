package com.example.parkit.Parking

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.parkit.Adapter
import com.example.parkit.R
import com.example.parkit.databinding.FragmentParkingListBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.mapbox.maps.MapView
import com.mapbox.maps.Style


class ParkingList : Fragment() {
    var mapView: MapView? = null

    val viewModel:ParkingViewModel by navGraphViewModels(R.id.fragmentParkings)
    private lateinit var binding: FragmentParkingListBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View ?{
        binding = FragmentParkingListBinding.inflate(layoutInflater)
        val view = binding.root
//        var mapView = binding.mapView
//        mapView?.getMapboxMap()?.loadStyleUri(Style.MAPBOX_STREETS)
        return view


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.layoutManager = LinearLayoutManager(activity)
        binding.recyclerView.adapter = Adapter(viewModel.getData())
        // Sheet
        if (binding.sheet != null) {
            BottomSheetBehavior.from(binding.sheet).apply{
                peekHeight = 150
                this.state = BottomSheetBehavior.STATE_COLLAPSED
            }
        }

        val pref by lazy { requireActivity().getSharedPreferences("parkitData", AppCompatActivity.MODE_PRIVATE) }
        /*
        mesReservation.setOnClickListener{
            val con = pref.getBoolean("connected", false)
            if (con)  {
                it.findNavController().navigate(R.id.action_parking_list_to_reservationDetails)

            } else
            {
                it.findNavController().navigate(R.id.action_parking_list_to_connexionFragment)

            }
        }

         */


    }
//
//    override fun onStart() {
//        super.onStart()
//        mapView?.onStart()
//    }
//
//    override fun onStop() {
//        super.onStop()
//        mapView?.onStop()
//    }
//
//    override fun onLowMemory() {
//        super.onLowMemory()
//        mapView?.onLowMemory()
//    }
//
//    override fun onDestroyView() {
//        super.onDestroy()
//        mapView?.onDestroy()
//    }

}