package com.example.parkit.dao

import androidx.room.*
import com.example.parkit.entity.User
import com.example.parkit.entity.UserAndReservations


@Dao
interface UserDao:BaseDao<User> {

    @Query("SELECT * FROM USERS")
    fun getAllUsers():List<User>

    @Query("SELECT * FROM USERS")
    fun getUserReservations():List<UserAndReservations>

    @Query("select * from users where nom=:firstName and prenom=:lastName")
    fun getUsersByName(firstName:String, lastName :String):List<User>

    @Insert
    fun addUsers(vararg users:User)

    @Update
    fun updateUser(user:User)

    @Delete
    fun deleteUser(user:User)

}