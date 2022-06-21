package com.example.parkit.dao


import androidx.room.*

import com.example.parkit.entity.rating

@Dao
interface RatingDao:BaseDao<rating> {

    @Query("SELECT * FROM ratings")
    fun getAllRatings():List<rating>

    @Query("SELECT * FROM ratings WHERE userId = :uid AND parkingId = :pid")
    fun getrate(uid:Int, pid:Int):rating

    @Query("SELECT * FROM ratings WHERE isSync = 0")
    fun getNotSynchedRatings():List<rating>

    @Update
    fun syncRate( rating: rating)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertRating(vararg rating: rating)

    @Delete
    fun deleteRating(rating: rating)

}