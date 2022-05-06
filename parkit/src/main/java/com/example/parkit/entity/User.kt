package com.example.parkit.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey
    val userId:Int,
    val nom:String,
    val prenom:String,
    val email:String,
    val tel:String,
    val photo:String,
    val mdp:String

)
