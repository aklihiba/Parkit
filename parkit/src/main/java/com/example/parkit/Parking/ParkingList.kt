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
import kotlinx.coroutines.*


class ParkingList : Fragment() {

    lateinit var progressBar: ProgressBar
    lateinit var recyclerView: RecyclerView

    val viewModel:ParkingViewModel by navGraphViewModels(R.id.fragmentParkings)
    private lateinit var binding: FragmentParkingListBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View ?{
        binding = FragmentParkingListBinding.inflate(layoutInflater)
        val view = binding.root
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

        val pref by lazy { requireActivity().getSharedPreferences("parkitData", AppCompatActivity.MODE_PRIVATE) }
        if(viewModel.list.isEmpty()) {
            recyclerView.adapter = Adapter(requireActivity(), viewModel.list)
            loadParkings()

        }
        else {
            recyclerView.adapter = Adapter(requireActivity(),viewModel.list)
        }

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

    fun loadParkings() {
        val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
            requireActivity().runOnUiThread {
                progressBar.visibility = View.INVISIBLE
                Toast.makeText(requireActivity(), "Une erreur s'est produite", Toast.LENGTH_SHORT).show()
            }

        }
        progressBar.visibility = View.VISIBLE
        CoroutineScope(Dispatchers.IO+ exceptionHandler).launch {
            val response = Endpoint.createEndpoint().getAllParkings()
            withContext(Dispatchers.Main) {
                progressBar.visibility = View.INVISIBLE
                if (response.isSuccessful && response.body() != null) {
                    viewModel.list = response.body()!!.toMutableList()
                    recyclerView.adapter = Adapter(requireActivity(), viewModel.list)
                } else {
                    Toast.makeText(requireActivity(), "Une erreur s'est produite", Toast.LENGTH_SHORT).show()
                }
            }
        }




    }

}