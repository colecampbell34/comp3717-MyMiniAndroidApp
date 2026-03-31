package com.bcit.myminiandroidapp.appui

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

@Composable
fun PortfolioScreen(state: PortfolioState) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("My Portfolio", fontSize = 30.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))

        // Single Source of Truth: Passing an event callback down to the stateless component
        AddAssetForm(onAdd = { symbol, qty ->
            state.addAsset(symbol, qty.toDoubleOrNull() ?: 0.0)
        })

        Spacer(modifier = Modifier.height(24.dp))

        // LazyColumn for efficient list rendering
        LazyColumn {
            items(state.assets.size) { index ->
                val asset = state.assets[index]
                AssetCard(
                    asset = asset,
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
            label = { Text("Asset Symbol (e.g., BTC, AAPL)") },
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
@Composable
fun AssetCard(asset: LocalAsset, onDelete: () -> Unit) {
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
                Text(text = "Shares/Coins: ${asset.quantity}", fontSize = 16.sp)
            }

            // Callback execution
            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Close, contentDescription = "Delete")
            }
        }
    }
}