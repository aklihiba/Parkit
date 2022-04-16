package com.example.parkit.Parking

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.navGraphViewModels
import com.example.parkit.R
import com.google.android.material.floatingactionbutton.FloatingActionButton


class Details : Fragment() {
   val viewModel:ParkingViewModel by navGraphViewModels(R.id.parking_list)

    lateinit var  park : Parking
    lateinit var nom : TextView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val _view = inflater.inflate(R.layout.fragment_details, container, false)

        nom= _view.findViewById(R.id.nom) //as? TextView
        var adresse = _view.findViewById(R.id.adresse) as? TextView
        var distance = _view.findViewById(R.id.distance) as? TextView
        var temps_trajet = _view.findViewById(R.id.temps_trajet) as? TextView
        var occupation = _view.findViewById(R.id.occupation) as? TextView
        var etat = _view.findViewById(R.id.etat) as? TextView
        var score = _view.findViewById(R.id.ratingBar) as? RatingBar
        var h_ouverture = _view.findViewById(R.id.horaire_ouvert) as? TextView
        var h_fermeture = _view.findViewById(R.id.horaire_ferme) as? TextView
        var tarif = _view.findViewById(R.id.tarif) as? TextView
        val reserverButton = _view.findViewById(R.id.reserver) as? Button
        val gotoMaps = _view.findViewById(R.id.gotoMaps) as? FloatingActionButton
        val image  = _view.findViewById(R.id.imageView) as? ImageView
       // viewModel = ViewModelProvider(_view).get(ParkingViewModel::class.java)
        park = viewModel.getParking()
        print("\n park selected ${park.nom}\n" +
                "\n nom present dans tewxtvuew ${nom.text}")
        nom.setText(park.nom)

        if (image != null)
       {
           image.setImageResource(park.image)
           print("\n \n $image\n\n")
       }else {
           print("IMAGE NULL PROBL IN GETTING VIEW")
       }

        if (adresse != null) {
            adresse.text =park.adresse
        }
        if (distance != null) {
            distance.text =park.distance
        }
        if (temps_trajet != null) {
            temps_trajet.text=park.temps
        }
        if (occupation != null) {
            occupation.text=park.occupation
            print("${occupation.text}")
        }
        if (etat != null) {
            etat.text=park.etat
        }
        if (score != null) {
            score.rating = park.note.toFloat()
        }
        if (h_ouverture != null) {
            h_ouverture.text=park.heure_ouverture
        }
        if (h_fermeture != null) {
            h_fermeture.text=park.heure_fermeture
        }

        if (tarif != null) {
            (park.tarif.toString() + "DZD").also { tarif.text = it }
        }

        if (reserverButton != null) {
            reserverButton.setOnClickListener(){
               // TODO("add function reserver place de parking")
            }
        }
        if (gotoMaps != null) {
            gotoMaps.setOnClickListener(){
               // TODO("add function go to maps")
                /*
            * gotoMaps.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                val gmmIntentUri =
      Uri.parse("google.navigation:q=@latitude,@longitude")
    val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
    mapIntent.setPackage("com.google.android.apps.maps")
    startActivity(mapIntent)
                }
            }, 1000);
        }
    });*/
            }
        }
       // TODO("getting serializable object")

        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        nom.text = park.nom
    }
}