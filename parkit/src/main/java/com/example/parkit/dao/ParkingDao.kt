package com.example.parkit.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.parkit.entity.Parking
import com.example.parkit.entity.Reservation

@Dao
interface ParkingDao:BaseDao<Parking> {

    @Query("SELECT * FROM PARKINGS")
    fun getAllReservations():List<Reservation>


}