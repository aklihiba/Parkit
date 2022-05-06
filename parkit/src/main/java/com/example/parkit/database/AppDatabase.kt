package com.example.parkit.database

import com.example.parkit.dao.ParkingDao


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.parkit.dao.ReservationDao
import com.example.parkit.dao.UserDao
import com.example.parkit.entity.Parking
import com.example.parkit.entity.Reservation
import com.example.parkit.entity.User

@Database(entities = [User::class,Reservation::class,Parking::class],version=1)
abstract class AppDatabase:RoomDatabase() {
    @TypeConverters(Converters::class)
    abstract fun getUserDo(): UserDao
    abstract fun getReservationDo():ReservationDao
    abstract fun getParkingDo(): ParkingDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        fun buildDatabase(context: Context): AppDatabase? {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = Room.databaseBuilder(context,AppDatabase::class.java, "parkit_db").allowMainThreadQueries().build()
                }
            }
            return INSTANCE
        }
    }

}