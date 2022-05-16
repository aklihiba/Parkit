package com.example.parkit

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.parkit.entity.Parking


class Adapter( val context :Context , val parkingList : List<Parking>): RecyclerView.Adapter<ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false))

    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = parkingList[position]
        holder.apply {
            parking_image.setImageResource(R.drawable.parking2)
            //Glide.with(context).load(url +currentItem.image).into(parking_image)
            etat.text = currentItem.etat
            occupation.text = currentItem.occupation
            nom.text = currentItem.nom
            adresse.text = currentItem.adresse
            distance.text = currentItem.distance
            temps_trajet.text = currentItem.temps
            score.rating = currentItem.note.toFloat()

            itemView.setOnClickListener { view->
                val bundle = bundleOf("position" to position)
                view.findNavController().navigate(R.id.action_parking_list_to_details, bundle)
            }
        }



    }

    override fun getItemCount(): Int {
        return parkingList.size
    }


}
class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    val parking_image: ImageView = itemView.findViewById(R.id.parking_image)
    val etat: TextView = itemView.findViewById(R.id.etat)
    val occupation: TextView = itemView.findViewById(R.id.occupation)
    val nom: TextView = itemView.findViewById(R.id.nom)
    val adresse: TextView = itemView.findViewById(R.id.adresse)
    val distance: TextView = itemView.findViewById(R.id.distance)
    val temps_trajet: TextView = itemView.findViewById(R.id.temps_trajet)
    val score: RatingBar = itemView.findViewById(R.id.ratingBar)
}