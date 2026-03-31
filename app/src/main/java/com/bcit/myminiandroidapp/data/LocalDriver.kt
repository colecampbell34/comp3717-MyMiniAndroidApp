package com.bcit.myminiandroidapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_drivers")
data class LocalDriver(
    @PrimaryKey val driverNumber: Int, // Using their actual racing number as the ID!
    @ColumnInfo(name = "full_name") val fullName: String,
    @ColumnInfo(name = "team_name") val teamName: String
)