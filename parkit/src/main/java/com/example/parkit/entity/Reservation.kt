package com.example.parkit.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

import java.util.*

@Entity(tableName = "reservations",

)
data class Reservation(
    @PrimaryKey(autoGenerate = false)
    val reservationId:Int = 0,
    @ColumnInfo(name = "user_Id") val user_Id:Int,
    @ColumnInfo(name = "parking_Id") val parking_Id:Int,
    @ColumnInfo(name="parkingName") val parkingName: String,
    @ColumnInfo(name = "hEntree") val hEntree: Date,
    @ColumnInfo(name = "hSortie") val hSortie: Date,
    @ColumnInfo(name = "num_place") val num_place: Int = 0,
    @ColumnInfo(name = "prix") val prix: Double?=0.0
)