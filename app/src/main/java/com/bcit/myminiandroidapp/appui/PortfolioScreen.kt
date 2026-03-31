package com.bcit.myminiandroidapp.appui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bcit.myminiandroidapp.data.LocalAsset

@SuppressLint("DefaultLocale")
@Composable
fun PortfolioScreen(state: PortfolioState) {
    // Calculate total portfolio value combining Room Data with API Data
    val totalValue = state.assets.sumOf { asset ->
        val currentPrice = state.marketPrices[asset.symbol] ?: 0.0
        currentPrice * asset.quantity
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("My Portfolio", fontSize = 30.sp, fontWeight = FontWeight.Bold)

        // Display calculated live total
        Text(
            text = "Total Value: $${String.format("%.2f", totalValue)}",
            fontSize = 20.sp,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Single Source of Truth: Passing an event callback down to the stateless component
        AddAssetForm(onAdd = { symbol, qty ->
            state.addAsset(symbol, qty.toDoubleOrNull() ?: 0.0)
        })

        Spacer(modifier = Modifier.height(24.dp))

        LazyColumn {
            items(state.assets.size) { index ->
                val asset = state.assets[index]
                // Look up live price, default to 0.0 if API hasn't loaded or symbol is wrong
                val livePrice = state.marketPrices[asset.symbol] ?: 0.0

                AssetCard(
                    asset = asset,
                    currentPrice = livePrice,
                    onDelete = { state.removeAsset(asset) }
                )
            }
        }
    }
}

// Stateless Composable
@Composable
fun AddAssetForm(onAdd: (String, String) -> Unit) {
    var symbol by remember { mutableStateOf("") }
    var quantity by remember { mutableStateOf("") }

    Column {
        OutlinedTextField(
            value = symbol,
            onValueChange = { symbol = it },
            label = { Text("Asset Symbol (e.g., BTC, ETH)") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = quantity,
            onValueChange = { quantity = it },
            label = { Text("Quantity") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = {
                if (symbol.isNotBlank() && quantity.isNotBlank()) {
                    onAdd(symbol, quantity)
                    symbol = ""
                    quantity = ""
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add Asset")
        }
    }
}

// Stateless Composable
@SuppressLint("DefaultLocale")
@Composable
fun AssetCard(asset: LocalAsset, currentPrice: Double, onDelete: () -> Unit) {
    val totalAssetValue = asset.quantity * currentPrice

    Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(text = asset.symbol, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                Text(text = "Holdings: ${asset.quantity}", fontSize = 16.sp)
                Text(
                    text = "Value: $${String.format("%.2f", totalAssetValue)}",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.secondary
                )
            }

            // Callback execution
            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Close, contentDescription = "Delete")
            }
        }
    }
}