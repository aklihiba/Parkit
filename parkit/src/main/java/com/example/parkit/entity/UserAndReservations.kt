package com.example.parkit.entity

import androidx.room.Embedded
import androidx.room.Relation

data class UserAndReservations (
    @Embedded
    val user:User?,
    @Relation(parentColumn = "userId",entityColumn = "userId")
    val reservations:List<Reservation>


)