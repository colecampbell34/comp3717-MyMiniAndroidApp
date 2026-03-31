package com.bcit.myminiandroidapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DriverDao {
    @Query("SELECT * FROM favorite_drivers")
    fun getAllFavorites(): List<LocalDriver>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addFavorite(driver: LocalDriver)

    @Delete
    fun removeFavorite(driver: LocalDriver)
}