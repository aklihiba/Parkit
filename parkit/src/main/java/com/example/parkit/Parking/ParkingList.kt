package com.example.parkit.Parking

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.parkit.Adapter
import com.example.parkit.R
import com.example.parkit.databinding.FragmentParkingListBinding
import com.example.parkit.entity.Parking
import com.example.parkit.retrofit.Endpoint
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.mapbox.maps.MapView
import com.mapbox.maps.Style
import kotlinx.coroutines.*


class ParkingList : Fragment() {
    var mapView: MapView? = null


    val viewModel:ParkingViewModel by navGraphViewModels(R.id.fragmentParkings)
    private lateinit var binding: FragmentParkingListBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View ?{
        binding = FragmentParkingListBinding.inflate(layoutInflater)
        val view = binding.root
        binding.progressB.visibility = View.INVISIBLE
        var mapView = binding.mapView
        mapView?.getMapboxMap()?.loadStyleUri("mapbox://styles/hiba-akli/cl37sgdw800e214p6u9lne2k8")
        return view


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.layoutManager = LinearLayoutManager(activity)
        binding.recyclerView.adapter = Adapter(requireActivity(),viewModel.getData())
        // Sheet
        if (binding.sheet != null) {
            BottomSheetBehavior.from(binding.sheet).apply{
                peekHeight = 150
                this.state = BottomSheetBehavior.STATE_COLLAPSED
            }
        }

        if(viewModel.list.isEmpty()) {
            binding.recyclerView.adapter = Adapter(requireActivity(), viewModel.list)
            loadParkings()

        }
        else {
            binding.recyclerView.adapter = Adapter(requireActivity(),viewModel.list)
        }


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

    fun loadParkings() {
        val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
            requireActivity().runOnUiThread {
                binding.progressB.visibility = View.INVISIBLE
                Toast.makeText(requireActivity(), "Une erreur s'est produite", Toast.LENGTH_SHORT).show()
            }

        }
        binding.progressB.visibility = View.VISIBLE
        CoroutineScope(Dispatchers.IO+ exceptionHandler).launch {
            val response = Endpoint.createEndpoint().getAllParkings()
            withContext(Dispatchers.Main) {
                binding.progressB.visibility = View.INVISIBLE
                if (response.isSuccessful && response.body() != null) {
                    viewModel.list = response.body()!!.toMutableList()
                    binding.recyclerView.adapter = Adapter(requireActivity(), viewModel.list)
                } else {
                    Toast.makeText(requireActivity(), "Une erreur s'est produite", Toast.LENGTH_SHORT).show()
                }
            }
        }




    }

}