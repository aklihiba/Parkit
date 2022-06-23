package com.example.parkit.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ratings")
class rating (
    @PrimaryKey(autoGenerate = true)
    val rateId:Int = 0,
    @ColumnInfo(name = "user_Id") val user_Id:Int,
    @ColumnInfo(name = "parking_Id") val parking_Id:Int,
    @ColumnInfo(name="rate") var rate : Float,
    @ColumnInfo(name = "comment") var comment : String? = "",
    @ColumnInfo(name = "isSync") var isSync : Boolean? = false ,
)