package com.example.parkit

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.parkit.Parking.Parking


class Adapter(private val parkingList : ArrayList<Parking>, private val view :View): RecyclerView.Adapter<ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ViewHolder(itemView)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = parkingList[position]
        holder.parking_image.setImageResource(currentItem.image)
        holder.etat.text = currentItem.etat
        holder.occupation.text = currentItem.occupation
        holder.nom.text = currentItem.nom
        holder.adresse.text = currentItem.adresse
        holder.distance.text = currentItem.distance
        holder.temps_trajet.text = currentItem.temps
        holder.score.rating = currentItem.note.toFloat()

        holder.list_item.setOnClickListener {
            view.findNavController().navigate(R.id.action_parking_list_to_details)
        }


    }

    override fun getItemCount(): Int {
        return parkingList.size
    }


}
class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    val list_item : ConstraintLayout = itemView.findViewById(R.id.cadre)
    val parking_image: ImageView = itemView.findViewById(R.id.parking_image)
    val etat: TextView = itemView.findViewById(R.id.etat)
    val occupation: TextView = itemView.findViewById(R.id.occupation)
    val nom: TextView = itemView.findViewById(R.id.nom)
    val adresse: TextView = itemView.findViewById(R.id.adresse)
    val distance: TextView = itemView.findViewById(R.id.distance)
    val temps_trajet: TextView = itemView.findViewById(R.id.temps_trajet)
    val score: RatingBar = itemView.findViewById(R.id.ratingBar)
}