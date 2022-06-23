package com.example.parkit.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "parkings")
data class Parking(
    @PrimaryKey
    val parkingId:Int,
    val image:String,
    val latitude:Double,
    val longitude: Double,
    val etat:String,
    val occupation: String,
    val nom: String,
    val adresse: String,
    val distance:String,
    val temps:String,
    val note: Double,
    val heure_ouverture: String,
    val heure_fermeture: String,
    val tarif:Double
    )
