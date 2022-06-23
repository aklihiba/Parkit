package com.example.parkit.Parking

import android.content.Intent
import android.media.Rating
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RatingBar
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintSet
import androidx.navigation.findNavController
import androidx.navigation.navGraphViewModels
import androidx.viewbinding.ViewBindings
import androidx.work.*
import com.bumptech.glide.Glide
import com.example.parkit.R
import com.example.parkit.database.AppDatabase
import com.example.parkit.databinding.FragmentDetailsBinding
import com.example.parkit.entity.Parking
import com.example.parkit.entity.Reservation
import com.example.parkit.entity.rating
import com.example.parkit.worker.synchroniseWorker
import com.google.android.material.textfield.TextInputEditText
import java.time.LocalDateTime.now


class Details : Fragment() {

    lateinit var park: Parking
    val viewModel:ParkingViewModel by navGraphViewModels(R.id.fragmentParkings)
    private lateinit var binding: FragmentDetailsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentDetailsBinding.inflate(layoutInflater)
        val view = binding.root
        return view
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val pref by lazy { requireActivity().getSharedPreferences("parkitData", AppCompatActivity.MODE_PRIVATE) }
        val db = AppDatabase.buildDatabase(requireContext());

        super.onViewCreated(view, savedInstanceState)

        // get selected Position
        val selectedPosition = arguments?.getInt("position") as Int
        if(selectedPosition!=null) {
             park = viewModel.getData().get(selectedPosition)
            binding.nom.text = park.nom
            //binding.imageView.setImageResource(park.image)
            Glide.with(this).load(park.image).into(binding.imageView)
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

                    // sauvgarde de la reservation
                    val db = AppDatabase.buildDatabase(requireContext());
                   /* val res = Reservation(parkingId = selectedPosition,parkingName = park.nom, userId = pref.getString("id", "")!!.toInt(),
                        hEntree = now().dayOfMonth.toString())
                    db?.getReservationDao()?.insertreservation(res);


                    */
                    it.findNavController().navigate(R.id.action_details_to_reservationDetails)

            }
            }

        //alert dialog
        val con = pref.getBoolean("connected", false)
        if (!con)  {
            binding.rateFoaltButton.hide()
        }else{

            binding.rateFoaltButton.setOnClickListener{
                var oldrate: rating?
                oldrate = db?.getRatingDao()?.getrate( pref.getString("id", "")!!.toInt(), park.parkingId)

                val rateview: View = LayoutInflater.from(requireContext()).inflate(R.layout.rate_dialog,null)
                val builder = AlertDialog.Builder(requireContext())
                    .setView(rateview)

                var commentInput = rateview.findViewById<TextInputEditText>(R.id.editcomment) as TextInputEditText
                val rateInput =rateview.findViewById<RatingBar>(R.id.ratingBar)  as RatingBar

                if(oldrate != null){
                    commentInput.setText(oldrate?.comment)
                    rateInput.rating = oldrate?.rate!!
                }
                val valider = rateview.findViewById<Button>(R.id.valider) as Button

                val ratedialog = builder.show()
                valider.setOnClickListener{

                    var comment = commentInput.text
                    var rating = rateInput.rating
                    //save the rating in local with isSynch to false

                    if (oldrate != null){
                        oldrate.comment = comment.toString()
                        oldrate.rate = rating
                        oldrate.isSync = false
                        db?.getRatingDao()?.update(oldrate);
                    }else{
                        val newrate = rating(
                            user_Id =pref.getString("id", "")!!.toInt(),
                            parking_Id = park.parkingId,
                            comment = comment.toString(),
                            rate = rating,
                            isSync = false
                        )
                        db?.getRatingDao()?.insertRating(newrate);
                    }
                    println("before worker")
                    //lancement du service de synchronisation
                    val constraints = Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
                    val req = OneTimeWorkRequest.Builder(synchroniseWorker :: class.java).setConstraints(constraints).build()
                    val workManager = WorkManager.getInstance(requireContext())
                     workManager.enqueueUniqueWork("work"
                        ,
                        ExistingWorkPolicy.REPLACE,req)
                    // close dialog
                    ratedialog.dismiss()
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