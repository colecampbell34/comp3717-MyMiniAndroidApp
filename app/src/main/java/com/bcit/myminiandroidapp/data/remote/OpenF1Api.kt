package com.bcit.myminiandroidapp.data.remote

import com.google.gson.annotations.SerializedName
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

// API Models (Now fully nullable to prevent crashes!)
data class F1Session(
    @SerializedName("session_name") val sessionName: String?,
    @SerializedName("meeting_name") val meetingName: String?,
    @SerializedName("country_name") val countryName: String?
)

data class F1Driver(
    @SerializedName("driver_number") val driverNumber: Int?,
    @SerializedName("full_name") val fullName: String?,
    @SerializedName("team_name") val teamName: String?
)

// API Interface
interface OpenF1Api {
    @GET("v1/sessions")
    suspend fun getSessions(): List<F1Session>

    @GET("v1/drivers?session_key=latest")
    suspend fun getDrivers(): List<F1Driver>
}

// Retrofit Singleton
object RetrofitInstance {
    private const val BASE_URL = "https://api.openf1.org/"

    val api: OpenF1Api by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(OpenF1Api::class.java)
    }
}