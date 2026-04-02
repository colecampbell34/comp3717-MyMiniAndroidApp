package com.bcit.myminiandroidapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DriverDao {
    @Query("SELECT * FROM favorite_drivers")
    suspend fun getAllFavorites(): List<LocalDriver>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavorite(driver: LocalDriver)

    @Delete
    suspend fun removeFavorite(driver: LocalDriver)
}