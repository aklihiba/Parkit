package com.example.parkit

import android.annotation.SuppressLint
import android.content.Context
import android.telecom.Call
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.parkit.Parking.Parking
import com.example.parkit.Parking.details
import com.google.android.material.imageview.ShapeableImageView

class Adapter(private val parkingList : ArrayList<Parking>, val context: Context): RecyclerView.Adapter<ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ViewHolder(itemView)
    }

    @SuppressLint("ResourceAsColor")
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

        //todo set onclick listner to open second fragment
        holder.itemView.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {


            }
        })
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