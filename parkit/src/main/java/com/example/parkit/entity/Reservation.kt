package com.example.parkit.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "reservations",foreignKeys = [
    ForeignKey(entity = User::class,parentColumns = ["userId"],childColumns = ["userId"],
        onUpdate = ForeignKey.CASCADE,
        onDelete = ForeignKey.CASCADE
    ),
    ForeignKey(entity = Parking::class,parentColumns = ["parkingId"],childColumns = ["parkingId"],
        onUpdate = ForeignKey.CASCADE,
        onDelete = ForeignKey.CASCADE
    )])

data class Reservation (
    @PrimaryKey
    val reservationId:Int,
    val userId:Int,
    val parkingId:Int,
    val hEntree : Int,
    val hSortie : Int,
)