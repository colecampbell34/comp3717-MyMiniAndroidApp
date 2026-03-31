package com.bcit.myminiandroidapp.appui

import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.toMutableStateList
import com.bcit.myminiandroidapp.data.AssetRepository
import com.bcit.myminiandroidapp.data.LocalAsset
import com.bcit.myminiandroidapp.data.remote.RetrofitInstance

class PortfolioState(private val repository: AssetRepository) {
    var assets = repository.getAll().toMutableStateList()

    // Remote API State
    var marketPrices = mutableStateMapOf<String, Double>()
    var isApiLoading = mutableStateOf(false)

    fun addAsset(symbol: String, quantity: Double) {
        val newAsset = LocalAsset(symbol = symbol.uppercase(), quantity = quantity)
        repository.insertEntity(newAsset)
        refresh()
    }

    fun removeAsset(asset: LocalAsset) {
        repository.deleteEntity(asset)
        refresh()
    }

    // Ensures the UI is synced with the Database
    private fun refresh() {
        assets.apply {
            clear()
            addAll(repository.getAll())
        }
    }

    // API Call triggered by the UI
    suspend fun fetchMarketPrices() {
        isApiLoading.value = true
        try {
            val response = RetrofitInstance.api.getAssets()
            response.data.forEach { asset ->
                // Map the symbol (e.g., "BTC") to its price
                marketPrices[asset.symbol] = asset.priceUsd.toDoubleOrNull() ?: 0.0
            }
        } catch (e: Exception) {
            e.printStackTrace() // In a real app, handle the error state here
        } finally {
            isApiLoading.value = false
        }
    }
}