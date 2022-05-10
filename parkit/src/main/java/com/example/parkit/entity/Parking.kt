package com.example.parkit.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "parkings")
data class Parking(
    @PrimaryKey
    val parkingId:Int,
    var image:Int,
    var latitude:Double,
    var longitude: Double,
    var etat:String,
    var occupation: String,
    var nom: String,
    var adresse: String,
    var distance:String,
    var temps:String,
    var note: Double,
    var heure_ouverture: String,
    var heure_fermeture: String,
    var tarif:Double
    )
