package com.bcit.myminiandroidapp.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

// Data classes modeling the JSON returned by CoinCap
data class CoinCapResponse(val data: List<CryptoAsset>)
data class CryptoAsset(val symbol: String, val name: String, val priceUsd: String)

// The API Interface defining our HTTP requests
interface CoinCapApi {
    @GET("v2/assets")
    suspend fun getAssets(): CoinCapResponse
}

object RetrofitInstance {
    private const val BASE_URL = "https://api.coincap.io/"

    val api: CoinCapApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()) // Converts JSON to Kotlin objects
            .build()
            .create(CoinCapApi::class.java)
    }
}