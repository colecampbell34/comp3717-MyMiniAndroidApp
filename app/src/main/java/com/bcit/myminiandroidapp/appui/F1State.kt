package com.bcit.myminiandroidapp.appui

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.toMutableStateList
import com.bcit.myminiandroidapp.data.DriverRepository
import com.bcit.myminiandroidapp.data.LocalDriver
import com.bcit.myminiandroidapp.data.remote.F1Driver
import com.bcit.myminiandroidapp.data.remote.F1Session
import com.bcit.myminiandroidapp.data.remote.RetrofitInstance

class F1State(private val repository: DriverRepository) {
    // Room DB State
    var favoriteDrivers = repository.getFavorites().toMutableStateList()

    // API State
    var apiSessions = mutableStateListOf<F1Session>()
    var apiDrivers = mutableStateListOf<F1Driver>()
    var isLoading = mutableStateOf(false)
    var errorMessage = mutableStateOf<String?>(null) // Added error tracking

    // Toggle Room DB Favorite
    fun toggleFavorite(driver: F1Driver) {
        val dNum = driver.driverNumber ?: return // Can't save if the API gave no driver number

        val localDriver = LocalDriver(
            driverNumber = dNum,
            fullName = driver.fullName ?: "Unknown",
            teamName = driver.teamName ?: "Unknown"
        )

        val isFavorite = favoriteDrivers.any { it.driverNumber == dNum }

        if (isFavorite) {
            repository.removeFavorite(localDriver)
        } else {
            repository.addFavorite(localDriver)
        }
        refreshFavorites()
    }

    private fun refreshFavorites() {
        favoriteDrivers.apply {
            clear()
            addAll(repository.getFavorites())
        }
    }

    // Fetch API Data
    suspend fun fetchApiData() {
        isLoading.value = true
        errorMessage.value = null
        try {
            // Fetch Sessions
            val sessions = RetrofitInstance.api.getSessions()
            apiSessions.clear()
            // Reversing it puts the most recent races at the top, taking 30 keeps the list manageable
            apiSessions.addAll(sessions.reversed().take(30))

            // Fetch Drivers
            val drivers = RetrofitInstance.api.getDrivers()
            apiDrivers.clear()
            apiDrivers.addAll(drivers)

        } catch (e: Exception) {
            e.printStackTrace()
            errorMessage.value = "Error: ${e.message}"
        } finally {
            isLoading.value = false
        }
    }
}