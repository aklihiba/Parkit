package com.example.parkit.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey
    var userId:Int,
    var nom:String,
    var prenom:String,
    var email:String,
    var tel:String,
    var photo:String,
    var mdp:String

)
