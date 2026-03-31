package com.bcit.myminiandroidapp.appui

import androidx.compose.runtime.toMutableStateList
import com.bcit.myminiandroidapp.data.AssetRepository
import com.bcit.myminiandroidapp.data.LocalAsset

class PortfolioState(private val repository: AssetRepository) {
    // SnapshotStateList correctly manages UI recompositions for collections
    var assets = repository.getAll().toMutableStateList()

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
}