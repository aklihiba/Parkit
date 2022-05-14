package com.example.parkit.dao

import androidx.room.*
import com.example.parkit.entity.Reservation

@Dao
interface ReservationDao:BaseDao<Reservation> {

    @Query("SELECT * FROM RESERVATIONS")
    fun getAllReservations():List<Reservation>

    @Query("SELECT * FROM RESERVATIONS WHERE reservationId = :id")
    fun getReservationById(id:Int):List<Reservation>

    @Query("SELECT * FROM RESERVATIONS Where hEntree = :day")
    fun getAllReservationsOfTheDay(day: String):List<Reservation>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertreservation(vararg reservation: Reservation)

    @Delete
    fun deletereservation(reservation: Reservation)

}