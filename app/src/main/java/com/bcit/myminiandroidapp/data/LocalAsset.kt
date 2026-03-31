package com.bcit.myminiandroidapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "asset_table")
data class LocalAsset(
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    @ColumnInfo(name = "symbol") val symbol: String,
    @ColumnInfo(name = "quantity") val quantity: Double
)