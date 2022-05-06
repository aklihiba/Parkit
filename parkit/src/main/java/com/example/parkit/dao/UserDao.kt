package com.example.parkit.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.parkit.entity.User
import com.example.parkit.entity.UserAndReservations


@Dao
interface UserDao:BaseDao<User> {

    @Query("SELECT * FROM USERS")
    fun getAllUsers():List<User>

    @Query("SELECT * FROM USERS")
    fun getUserReservations():List<UserAndReservations>

}