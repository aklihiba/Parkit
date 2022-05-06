package com.example.parkit.Parking

import androidx.lifecycle.ViewModel
import com.example.parkit.R

class ParkingViewModel : ViewModel() {
   var images = arrayOf(
    R.drawable.parking1,
    R.drawable.parking2,
    R.drawable.parking3,
    R.drawable.parking4,
    R.drawable.parking1,
    R.drawable.parking2,
    R.drawable.parking3,
    R.drawable.parking4
    )
    var etats = arrayOf("fermé","ouvert","fermé","ouvert","fermé","ouvert","fermé","ouvert")
    var occupations = arrayOf("90%","35%","47%","12%","90%","35%","47%","12%")
    var noms = arrayOf("Parking du marché", "Parking P14","Paking communal 100 places", "Parking H400", "Parking du marché", "Parking P14","Paking communal 100 places", "Parking H400")
    var adresses = arrayOf("Bab Ezzeouar","Dar LBeida","Bab Ezzeouar","Dar LBeida","Bab Ezzeouar","Dar LBeida","Bab Ezzeouar","Dar LBeida")
    var distances = arrayOf("8,56 Km","12.4 Km","7,92 Km","12,7 Km","8,56 Km","12.4 Km","7,92 Km","12,7 Km")
    var temps_trajets = arrayOf("14 min", "17 min","12 min","16 min","14 min", "17 min","12 min","16 min")
    var note = arrayOf<Double>(3.3, 4.2, 2.7, 5.0, 1.8,4.2, 2.7, 5.0, 1.8 )
    var tarif = arrayOf<Double>(100.0, 120.0,250.0, 150.0, 100.0, 120.0,250.0, 150.0)
    var h_ouv = arrayOf("08h00","09h00","10h00","07h00","08h00","09h00","10h00","07h00")
    var h_ferm = arrayOf("18h00","19h00","22h00","23h00","21h00","19h00","23h00","23h30")
    var latitude = arrayOf(36.679484,36.679484,36.679484,36.679484,36.679484,36.679484,36.679484,36.679484)
    var longitude = arrayOf(3.138896,3.138896,3.138896,3.138896,3.138896,3.138896,3.138896,3.138896)
lateinit var list : ArrayList<Parking>
lateinit var selected: Parking
 fun addParking(parking :Parking)
{
 list.add(parking)
}
 fun getData(){
  for (i in images.indices){
   val parking = Parking(images[i],latitude[i], longitude[i],
    etats[i],occupations[i],noms[i],adresses[i],
    distances[i],temps_trajets[i],note[i], h_ouv[i],
    h_ferm[i], tarif[i])
   addParking(parking)
   print("added $i")
  }
}
 fun selectParking (p : Parking){
  selected = p
 }
 fun getParking (): Parking {
  return selected
 }
}