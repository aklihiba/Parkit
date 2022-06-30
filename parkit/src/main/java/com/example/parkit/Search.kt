package com.example.parkit

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.fragment.app.Fragment
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.parkit.Parking.ParkingViewModel
import com.example.parkit.databinding.FragmentSearchBinding
import com.example.parkit.entity.Recherche
import com.example.parkit.retrofit.Endpoint
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.TypeFilter
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import kotlinx.coroutines.*
import java.util.*


class Search : Fragment() {
    private val AUTOCOMPLETE_REQUEST_CODE = 1
    private val TAG = "ADDRESS_AUTOCOMPLETE"
    val viewModel: ParkingViewModel by navGraphViewModels(R.id.fragmentParkings)
    private lateinit var binding: FragmentSearchBinding


    private val startAutocomplete = registerForActivityResult(
        StartActivityForResult(),
        (ActivityResultCallback<ActivityResult> { result: ActivityResult ->
            if (result.getResultCode() === Activity.RESULT_OK) {
                val intent: Intent? = result.getData()
                if (intent != null) {
                    val place = Autocomplete.getPlaceFromIntent(intent)

                    // Write a method to read the address components from the Place
                    // and populate the form with the address components
                    Log.d(TAG, "Place: " + place.addressComponents)
                    val maxP = binding.maxPrix.text
                    val prixmax =  if(binding.maxPrix.text.isNotBlank())  maxP.toString().toDouble() else  0.0
                    val maxD = binding.maxDist.text
                    val distmax =  if(binding.maxPrix.text.isNotBlank())  maxD.toString().toDouble() else  0.0
                    val rech = Recherche(0,place.latLng.latitude,place.latLng.longitude,prixmax,distmax)
                    binding.address1Field.setText(place.address)
                    loadfilteredParkings(rech)

                }
            } else if (result.getResultCode() === Activity.RESULT_CANCELED) {
                // The user canceled the operation.
                Log.i(TAG, "User canceled autocomplete")
            }
        } as ActivityResultCallback<ActivityResult>?)!!
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
// Initialize the SDK
        Places.initialize(requireActivity(), "AIzaSyDgZadIjr0Xgvmeo6JZp5CN18Cv8Vy8j0E")

        // Create a new PlacesClient instance
        val placesClient = Places.createClient(requireContext())

        // Attach an Autocomplete intent to the Address 1 EditText field

        // Attach an Autocomplete intent to the Address 1 EditText field


    }
    private fun startAutocompleteIntent() {

        // Set the fields to specify which types of place data to
        // return after the user has made a selection.
        val fields: List<Place.Field> = Arrays.asList(
            Place.Field.ADDRESS_COMPONENTS,
            Place.Field.LAT_LNG, Place.Field.VIEWPORT
        )

        // Build the autocomplete intent with field, country, and type filters applied
        val intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields)
            .setCountry("DZ")
            .build(requireActivity())
        startAutocomplete.launch(intent)
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(layoutInflater)
        val view = binding.root
        return view
    }
    fun loadParkings() {
        val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
            requireActivity().runOnUiThread {
                binding.progBar.visibility = View.INVISIBLE
                Toast.makeText(requireActivity(), "Une erreur s'est produite", Toast.LENGTH_SHORT).show()
            }

        }
        binding.progBar.visibility = View.VISIBLE
        CoroutineScope(Dispatchers.IO+ exceptionHandler).launch {
            val response = Endpoint.createEndpoint().getAllParkings()
            withContext(Dispatchers.Main) {
                binding.progBar.visibility = View.INVISIBLE
                if (response.isSuccessful && response.body() != null) {
                    viewModel.list = response.body()!!.toMutableList()
                    binding.recyclerFiltre.adapter = Adapter(requireActivity(), viewModel.list)
                } else {

                    Toast.makeText(requireActivity(), "Une erreur s'est produite", Toast.LENGTH_SHORT).show()
                }
            }
        }




    }
    fun loadfilteredParkings(recherche: Recherche) {
        val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
            requireActivity().runOnUiThread {
                binding.progBar.visibility = View.INVISIBLE
                Toast.makeText(requireActivity(), "Une erreur s'est produite", Toast.LENGTH_SHORT).show()
            }

        }
        binding.progBar.visibility = View.VISIBLE
        CoroutineScope(Dispatchers.IO+ exceptionHandler).launch {
            val response = Endpoint.createEndpoint().getNearbyParks(recherche)
            withContext(Dispatchers.Main) {
                binding.progBar.visibility = View.INVISIBLE
                if (response.isSuccessful && response.body() != null) {
                    if (response.body()!!.isEmpty()){
                        Toast.makeText(requireActivity(), "LA LISTE EST VIDE", Toast.LENGTH_LONG).show()

                    } else{

                        viewModel.list = response.body()!!.toMutableList()
                        binding.recyclerFiltre.adapter = Adapter(requireActivity(), viewModel.list)
                    }
                } else {

                    Toast.makeText(requireActivity(), "Une erreur s'est produite", Toast.LENGTH_SHORT).show()
                }
            }
        }




    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerFiltre.layoutManager = LinearLayoutManager(activity)
        binding.recyclerFiltre.adapter = Adapter(requireActivity(),viewModel.getData())
        // Sheet




        // Set the fields to specify which types of place data to
        // return after the user has made a selection.
        val fields = listOf(Place.Field.ID, Place.Field.NAME)
        binding.address1Field.setOnClickListener { startAutocompleteIntent() }

        if(viewModel.list.isEmpty()) {
            binding.recyclerFiltre.adapter = Adapter(requireActivity(), viewModel.list)
            loadParkings()

        }
        else {
            binding.progBar.visibility = View.INVISIBLE
            binding.recyclerFiltre.adapter = Adapter(requireActivity(),viewModel.list)
        }


    }


}