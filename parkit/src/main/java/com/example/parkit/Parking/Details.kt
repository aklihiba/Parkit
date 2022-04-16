package com.example.parkit.Parking

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RatingBar
import android.widget.TextView
import com.example.parkit.R
import com.google.android.material.floatingactionbutton.FloatingActionButton


class Details : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var nom = requireActivity().findViewById(R.id.nom) as TextView
        var adresse = requireActivity().findViewById(R.id.adresse) as TextView
        var distance = requireActivity().findViewById(R.id.distance) as TextView
        var temps_trajet = requireActivity().findViewById(R.id.temps_trajet) as TextView
        var occupation = requireActivity().findViewById(R.id.occupation) as TextView
        var etat = requireActivity().findViewById(R.id.etat) as TextView
        var score = requireActivity().findViewById(R.id.ratingBar) as RatingBar
        var h_ouverture = requireActivity().findViewById(R.id.horaire_ouvert) as TextView
        var h_fermeture = requireActivity().findViewById(R.id.horaire_ferme) as TextView
        var duree = requireActivity().findViewById(R.id.duree) as TextView
        var tarif = requireActivity().findViewById(R.id.tarif) as TextView
        val reserverButton = requireActivity().findViewById(R.id.reserver) as Button
        val gotoMaps = requireActivity().findViewById(R.id.gotoMaps) as FloatingActionButton

        nom.text = ""
        adresse.text =""
        distance.text =""
        temps_trajet.text=""
        occupation.text=""
        etat.text=""
        score.rating = 0.0F
        h_ouverture.text=""
        h_fermeture.text=""
        duree.text =""
        tarif.text =""

        reserverButton.setOnClickListener(){
        TODO("add function reserver place de parking")
        }
        gotoMaps.setOnClickListener(){
        TODO("add function go to maps")
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
        TODO("getting serializable object")
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

}