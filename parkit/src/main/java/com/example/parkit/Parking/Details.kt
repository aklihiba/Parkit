package com.example.parkit.Parking

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.navGraphViewModels
import com.example.parkit.R
import com.example.parkit.database.AppDatabase
import com.example.parkit.databinding.FragmentDetailsBinding
import com.example.parkit.entity.Parking
import com.example.parkit.entity.Reservation
import kotlinx.coroutines.launch
import java.util.*


class Details : Fragment() {

    lateinit var park: Parking
    val viewModel:ParkingViewModel by navGraphViewModels(R.id.parking_list)
    private lateinit var binding: FragmentDetailsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentDetailsBinding.inflate(layoutInflater)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val pref by lazy { requireActivity().getSharedPreferences("parkitData", AppCompatActivity.MODE_PRIVATE) }
        super.onViewCreated(view, savedInstanceState)
        // get selected Position
        val selectedPosition = arguments?.getInt("position")
        if(selectedPosition!=null) {
             park = viewModel.getData().get(selectedPosition)
            binding.nom.text = park.nom
            binding.imageView.setImageResource(park.image)
            binding.adresse.text = park.adresse
            binding.distance.text = park.distance
            binding.tempsTrajet.text = park.temps
            binding.occupation.text = park.occupation
            binding.etat.text = park.etat
            binding.ratingBar.rating = park.note.toFloat()
            binding.horaireOuvert.text = park.heure_ouverture
            binding.horaireFerme.text = park.heure_fermeture
            (park.tarif.toString() + "DZD").also { binding.prix.text = it }

            }

        binding.reserver.setOnClickListener() {
            val con = pref.getBoolean("connected", false)
if (con)  {
    val database = AppDatabase.getDatabase(requireContext())

    val newReservation = Reservation(1,1,1,10,11)
    database.getReservationDo().addReserv(newReservation)

    it.findNavController().navigate(R.id.action_details_to_reservationDetails)

} else
{
    it.findNavController().navigate(R.id.action_details_to_authFragment)

}

        }

        binding.gotoMaps.setOnClickListener{
                val gmmIntentUri = Uri.parse("google.navigation:q=${park.latitude},${park.longitude}")
                val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                mapIntent.setPackage("com.google.android.apps.maps")
                startActivity(mapIntent)

        }

    }



}