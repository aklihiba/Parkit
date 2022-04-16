package com.example.parkit.Parking

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.parkit.Adapter
import com.example.parkit.R
import com.google.android.material.bottomsheet.BottomSheetBehavior


class ParkingList : Fragment() {
    private  var recyclerView: RecyclerView? = null

    val viewModel:ParkingViewModel by navGraphViewModels(R.id.parking_list)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View ?{
        val _view = inflater.inflate(R.layout.fragment_parking_list, container, false)

        var sheet = requireActivity().findViewById(R.id.sheet) as? FrameLayout


        if (sheet != null) {
            BottomSheetBehavior.from(sheet).apply{
                peekHeight = 150
                this.state = BottomSheetBehavior.STATE_COLLAPSED
            }
        }


        recyclerView = _view.findViewById(R.id.recyclerView) as? RecyclerView
        recyclerView?.layoutManager = LinearLayoutManager(activity)


        arrayListOf<Parking>().also { viewModel.list = it }

        return _view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getData()
        recyclerView?.adapter = Adapter(viewModel.list, view)
    }


}