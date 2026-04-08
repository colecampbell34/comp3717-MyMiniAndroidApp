package com.bcit.myminiandroidapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [LocalDriver::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun driverDao(): DriverDao
}

object MyDatabase {
    fun getDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java, "f1_database"
        )
            .build()
    }
}