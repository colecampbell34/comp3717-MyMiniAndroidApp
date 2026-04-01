package com.bcit.myminiandroidapp.data.remote

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.gson.*

class F1ApiRepository {

    private companion object {
        const val BASE_URL = "https://api.openf1.org/v1"
        const val SESSIONS_ENDPOINT = "$BASE_URL/sessions"
        const val DRIVERS_ENDPOINT = "$BASE_URL/drivers?session_key=latest"
        const val POSITION_ENDPOINT = "$BASE_URL/position"
    }

    private val client = HttpClient {
        install(ContentNegotiation) {
            gson()
        }
    }

    suspend fun getSessions(year: Int): List<F1Session> {
        // Build the URL dynamically with the requested year
        val urlWithYear = "$SESSIONS_ENDPOINT?year=$year"
        return client.get(urlWithYear).body()
    }

    suspend fun getDrivers(): List<F1Driver> {
        return client.get(DRIVERS_ENDPOINT).body()
    }

    suspend fun getSessionPositions(sessionKey: Int): List<DriverPosition> {
        return try {
            val url = "$POSITION_ENDPOINT?session_key=$sessionKey"
            client.get(url).body()
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }
}