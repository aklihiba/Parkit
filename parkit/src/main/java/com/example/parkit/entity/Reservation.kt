package com.example.parkit.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "reservations",/*foreignKeys = [
    ForeignKey(entity = User::class,parentColumns = ["userId"],childColumns = ["userId"],
        onUpdate = ForeignKey.CASCADE,
        onDelete = ForeignKey.CASCADE
    ),
    ForeignKey(entity = Parking::class,parentColumns = ["parkingId"],childColumns = ["parkingId"],
        onUpdate = ForeignKey.CASCADE,
        onDelete = ForeignKey.CASCADE
    )])

    */
)
data class Reservation (
    @PrimaryKey(autoGenerate = true)
    val reservationId:Int = 0,
    @ColumnInfo(name = "userId") val userId:Int,
    @ColumnInfo(name = "parkingId") val parkingId:Int,
    @ColumnInfo(name="parkingName") val parkingName : String,
    @ColumnInfo(name = "hEntree") val hEntree : String? = "",
    @ColumnInfo(name = "hSortie") val hSortie : String? = "",
)