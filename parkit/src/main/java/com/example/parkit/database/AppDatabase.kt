package com.example.parkit.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.parkit.dao.RatingDao
import com.example.parkit.dao.ReservationDao
import com.example.parkit.entity.Reservation
import com.example.parkit.entity.rating


@Database(entities = [Reservation::class, rating::class],version=1, exportSchema = false)
abstract class AppDatabase:RoomDatabase() {
//    @TypeConverters(Converters::class)

    abstract fun getReservationDao():ReservationDao
    abstract fun getRatingDao():RatingDao

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