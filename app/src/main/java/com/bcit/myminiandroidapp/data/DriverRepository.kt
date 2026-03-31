package com.bcit.myminiandroidapp.data

class DriverRepository(private val driverDao: DriverDao) {
    fun getFavorites(): List<LocalDriver> {
        return driverDao.getAllFavorites()
    }

    fun addFavorite(driver: LocalDriver) {
        driverDao.addFavorite(driver)
    }

    fun removeFavorite(driver: LocalDriver) {
        driverDao.removeFavorite(driver)
    }
}