package com.example.parkit

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.parkit.database.AppDatabase
import com.example.parkit.entity.Reservation

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before

@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    lateinit var mDataBase: AppDatabase
    @Before
    fun initDB() {
        mDataBase =
            Room.inMemoryDatabaseBuilder(InstrumentationRegistry. getInstrumentation().context,AppDatabase::class.java).build()
    }

    @Test
    fun testInsertion() {
        val res = Reservation(reservationId = 1, userId = 1, parkingId = 3, parkingName = "parking"
        , hEntree = "15-05-2022")
        mDataBase?.getReservationDao()?.insertreservation(res)
        val list = mDataBase?.getReservationDao()?. getReservationById(1)
        assertEquals(res,list?.get(0))
    }
    @Test
    fun testConsultation() {
        val res = Reservation(reservationId = 1, userId = 1, parkingId = 3, parkingName = "parking"
            , hEntree = "10-05-2022")
        val res1 = Reservation(reservationId = 2, userId = 1, parkingId = 3, parkingName = "parking"
            , hEntree = "10-05-2022")

        mDataBase?.getReservationDao()?.insertreservation(res)
        mDataBase?.getReservationDao()?.insertreservation(res1)

        val list = mDataBase?.getReservationDao()?. getAllReservations()

        assertEquals(res,list?.get(0))
        assertEquals(res1,list?.get(1))

    }

    @Test
    fun testConsultationDay() {
        val res = Reservation(reservationId = 1, userId = 1, parkingId = 3, parkingName = "parking"
            , hEntree = "10-05-2022")
        val res1 = Reservation(reservationId = 2, userId = 1, parkingId = 3, parkingName = "parking"
            , hEntree = "11-05-2022")
        mDataBase?.getReservationDao()?.insertreservation(res)
        mDataBase?.getReservationDao()?.insertreservation(res1)

        val list = mDataBase?.getReservationDao()?. getAllReservationsOfTheDay("11-05-2022")

        assertEquals(res1,list?.get(0))

    }
}