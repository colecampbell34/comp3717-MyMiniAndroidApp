package com.bcit.myminiandroidapp.data.remote

import com.google.gson.annotations.SerializedName

data class F1Session(
    @SerializedName("session_key") val sessionKey: Int?,
    @SerializedName("session_name") val sessionName: String?,
    @SerializedName("meeting_name") val meetingName: String?,
    @SerializedName("country_name") val countryName: String?,
    @SerializedName("circuit_short_name") val circuitShortName: String?,
    @SerializedName("year") val year: Int?,
    @SerializedName("date_start") val dateStart: String?
)

data class F1Driver(
    @SerializedName("driver_number") val driverNumber: Int?,
    @SerializedName("full_name") val fullName: String?,
    @SerializedName("team_name") val teamName: String?,
    @SerializedName("headshot_url") val headshotUrl: String?
)

data class DriverPosition(
    @SerializedName("session_key") val sessionKey: Int?,
    @SerializedName("driver_number") val driverNumber: Int?,
    @SerializedName("position") val position: Int?,
    @SerializedName("date") val date: String?
)