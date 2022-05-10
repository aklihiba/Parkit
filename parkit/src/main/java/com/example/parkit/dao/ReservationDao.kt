package com.example.parkit.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.parkit.entity.Reservation

@Dao
interface ReservationDao:BaseDao<Reservation> {

    @Query("SELECT * FROM RESERVATIONS")
    fun getAllReservations():List<Reservation>

    @Insert
    fun addReserv (reservation : Reservation)


}