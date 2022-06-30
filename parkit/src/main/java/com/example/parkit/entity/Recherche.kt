package com.example.parkit.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Recherches")

data class Recherche(
    @PrimaryKey
    val id:Int,
    var lat:Double,
    var long: Double,
    var prixMax: Double,
    var distMax: Double)
