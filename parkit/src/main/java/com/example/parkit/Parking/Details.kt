package com.example.parkit.Parking

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.navGraphViewModels
import com.example.parkit.R
import com.example.parkit.dao.ReservationDao
import com.example.parkit.database.AppDatabase
import com.example.parkit.databinding.FragmentDetailsBinding
import com.example.parkit.entity.Parking
import com.example.parkit.entity.Reservation
import java.time.LocalDateTime.now


class Details : Fragment() {

    lateinit var park: Parking
    val viewModel:ParkingViewModel by navGraphViewModels(R.id.parking_list)
    private lateinit var binding: FragmentDetailsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentDetailsBinding.inflate(layoutInflater)
        val view = binding.root
        return view
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val pref by lazy { requireActivity().getSharedPreferences("parkitData", AppCompatActivity.MODE_PRIVATE) }
        super.onViewCreated(view, savedInstanceState)
        // get selected Position
        val selectedPosition = arguments?.getInt("position") as Int
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
            binding.horaireOuvert?.text = park.heure_ouverture
            binding.horaireFerme.text = park.heure_fermeture
            (park.tarif.toString() + "DZD").also { binding.prix.text = it }

            binding.reserver.setOnClickListener() {
                val con = pref.getBoolean("connected", false)
                if (con)  {
                    // sauvgarde de la reservation
                    val db = AppDatabase.buildDatabase(requireContext());
                    val res = Reservation(parkingId = selectedPosition,parkingName = park.nom, userId = pref.getInt("id", 1),
                        hEntree = now().dayOfMonth.toString())
                    db?.getReservationDao()?.insertreservation(res);


                    it.findNavController().navigate(R.id.action_details_to_reservationDetails)

                } else
                {
                    it.findNavController().navigate(R.id.action_details_to_connexionFragment)

                }

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