package com.example.parkit.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ratings")
class rating (
    @PrimaryKey(autoGenerate = true)
    val rateId:Int = 0,
    @ColumnInfo(name = "userId") val userId:Int,
    @ColumnInfo(name = "parkingId") val parkingId:Int,
    @ColumnInfo(name="rate") val rate : Float,
    @ColumnInfo(name = "comment") val comment : String? = "",
    @ColumnInfo(name = "isSync") val isSync : Boolean? = false ,
)