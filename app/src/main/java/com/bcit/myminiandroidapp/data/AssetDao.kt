package com.bcit.myminiandroidapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface AssetDao {
    @Query("SELECT * FROM asset_table")
    fun getAll(): List<LocalAsset>

    @Insert
    fun add(asset: LocalAsset)

    @Delete
    fun delete(asset: LocalAsset)
}