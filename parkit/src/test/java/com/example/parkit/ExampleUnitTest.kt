package com.example.reservationuserexample

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.parkit.database.AppDatabase
import com.example.parkit.entity.Reservation
import com.example.parkit.entity.User
import com.example.parkit.entity.UserAndReservations
import org.junit.After

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    lateinit var mDataBase: AppDatabase


    @Before
    fun initDB() {
        mDataBase =      Room.inMemoryDatabaseBuilder(InstrumentationRegistry.
        getInstrumentation().context, AppDatabase::class.java).build()
    }

    @Test
    fun testAddGetUser() {
        val user = User(1,"test1","test1"," "," "," "," ")
        mDataBase.getUserDo().insert(user)
        val user1 = mDataBase.getUserDo().getAllUsers().get(0)
        assertEquals(user,user1)
    }

    @Test
    fun testAddGetReservations() {
        val user = User(1,"test1","test1"," "," "," "," ")
        val reservation = Reservation(1,1,1,11,12)
        mDataBase.getUserDo().insert(user)
        mDataBase.getReservationDo().insert(reservation)
        val reservation1 = mDataBase.getReservationDo().getAllReservations().get(0)
        assertEquals(reservation,reservation1)
    }


    @Test
    fun testAddGetUserReservations() {
        val expectedList = mutableListOf<UserAndReservations>()
        val user1 = User(1,"test1","test1"," "," "," "," ")
        val user2 = User(2,"test2","test2"," "," "," "," ")
        val reservation1 = Reservation(1,1,1,11,12)
        val reservation2 = Reservation(2,1,2,13,15)
        val reservation3 = Reservation(3,2,1,7,12)
        val reservation4 = Reservation(4,2,3,8,10)
        val reservation5 = Reservation(5,2,4,12,13)
        mDataBase.getUserDo().insert(user1,user2)
        mDataBase.getReservationDo().insert(reservation1,reservation2,reservation3,reservation4,reservation5)
        // Expected List
        expectedList.add(UserAndReservations(user1, mutableListOf(reservation1,reservation2)))
        expectedList.add(UserAndReservations(user2, mutableListOf(reservation3,reservation4,reservation5)))

        // getInsertedUserReservations
        val userReservations = mDataBase.getUserDo().getUserReservations()
        // Assertion
        assertArrayEquals(expectedList.toTypedArray(),userReservations.toTypedArray())


    }

    @After
    fun closeDb(){
        mDataBase?.close()
    }
}