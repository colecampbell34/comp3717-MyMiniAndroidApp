package com.bcit.myminiandroidapp.appui

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.toMutableStateList
import com.bcit.myminiandroidapp.data.DriverRepository
import com.bcit.myminiandroidapp.data.LocalDriver
import com.bcit.myminiandroidapp.data.remote.DriverPosition
import com.bcit.myminiandroidapp.data.remote.F1ApiRepository
import com.bcit.myminiandroidapp.data.remote.F1Driver
import com.bcit.myminiandroidapp.data.remote.F1Session

class F1State(
    private val localRepository: DriverRepository,
    private val remoteRepository: F1ApiRepository
) {
    var selectedYear = mutableStateOf(2025)
    var favoriteDrivers = localRepository.getFavorites().toMutableStateList()
    var apiSessions = mutableStateListOf<F1Session>()
    var apiDrivers = mutableStateListOf<F1Driver>()
    var isLoading = mutableStateOf(false)
    var errorMessage = mutableStateOf<String?>(null)

    var sessionPositions = mutableStateListOf<DriverPosition>()
    var isDetailsLoading = mutableStateOf(false)

    fun toggleFavorite(driver: F1Driver) {
        val dNum = driver.driverNumber ?: return
        val localDriver = LocalDriver(dNum, driver.fullName ?: "N/A", driver.teamName ?: "N/A")
        val isFavorite = favoriteDrivers.any { it.driverNumber == dNum }

        if (isFavorite) {
            localRepository.removeFavorite(localDriver)
        } else {
            localRepository.addFavorite(localDriver)
        }
        refreshFavorites()
    }

    private fun refreshFavorites() {
        favoriteDrivers.apply { clear(); addAll(localRepository.getFavorites()) }
    }

    suspend fun changeYear(newYear: Int) {
        if (newYear != selectedYear.value) {
            selectedYear.value = newYear
            fetchApiData()
        }
    }

    suspend fun fetchApiData() {
        isLoading.value = true
        errorMessage.value = null
        try {
            val sessions = remoteRepository.getSessions(selectedYear.value)
            apiSessions.clear()
            val filteredSessions = sessions.filter { it.sessionName == "Race" }
            apiSessions.addAll(filteredSessions.reversed())

            val drivers = remoteRepository.getDrivers()
            apiDrivers.clear()
            apiDrivers.addAll(drivers)
        } catch (e: Exception) {
            e.printStackTrace()
            errorMessage.value = "Could not load F1 data for ${selectedYear.value}. Please check your connection."
        } finally {
            isLoading.value = false
        }
    }

    suspend fun fetchSessionPositions(sessionKey: Int) {
        isDetailsLoading.value = true
        errorMessage.value = null
        try {
            val rawPositions = remoteRepository.getSessionPositions(sessionKey)

            val finalPositions = rawPositions
                .groupBy { it.driverNumber } // Group all position updates by driver
                .mapNotNull { (_, updates) -> updates.maxByOrNull { it.date ?: "" } } // For each driver, find the latest update
                .sortedBy { it.position } // Sort the final list by position

            sessionPositions.clear()
            sessionPositions.addAll(finalPositions)
        } catch (e: Exception) {
            e.printStackTrace()
            errorMessage.value = "An unexpected error occurred."
        } finally {
            isDetailsLoading.value = false
        }
    }
}