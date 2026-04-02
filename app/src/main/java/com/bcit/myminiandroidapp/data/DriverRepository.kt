package com.bcit.myminiandroidapp.data

class DriverRepository(private val driverDao: DriverDao) {
    suspend fun getFavorites(): List<LocalDriver> {
        return driverDao.getAllFavorites()
    }

    suspend fun addFavorite(driver: LocalDriver) {
        driverDao.addFavorite(driver)
    }

    suspend fun removeFavorite(driver: LocalDriver) {
        driverDao.removeFavorite(driver)
    }
}