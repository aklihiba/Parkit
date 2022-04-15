package com.example.parkit.Parking

data class Parking(
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
