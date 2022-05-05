package com.example.parkit.Parking

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.parkit.Adapter
import com.example.parkit.R
import com.example.parkit.databinding.FragmentParkingListBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior


class ParkingList : Fragment() {

    val viewModel:ParkingViewModel by navGraphViewModels(R.id.parking_list)
    private lateinit var binding: FragmentParkingListBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View ?{
        binding = FragmentParkingListBinding.inflate(layoutInflater)
        val view = binding.root
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
    }


}