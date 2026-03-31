package com.bcit.myminiandroidapp.appui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bcit.myminiandroidapp.data.remote.F1Driver

@Composable
fun DriversScreen(state: F1State) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Current Grid", fontSize = 30.sp, fontWeight = FontWeight.Bold)
        Text("Tap the heart to save to favorites", fontSize = 14.sp, color = MaterialTheme.colorScheme.secondary)
        Spacer(modifier = Modifier.height(16.dp))

        if (state.isLoading.value) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else if (state.errorMessage.value != null) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(state.errorMessage.value!!, color = MaterialTheme.colorScheme.error)
            }
        } else {
            LazyColumn {
                items(state.apiDrivers.size) { index ->
                    val driver = state.apiDrivers[index]

                    // Safely check if driver exists in DB
                    val isFavorite = state.favoriteDrivers.any { it.driverNumber == driver.driverNumber }

                    DriverCard(
                        driver = driver,
                        isFavorite = isFavorite,
                        onToggleFavorite = { state.toggleFavorite(driver) }
                    )
                }
            }
        }
    }
}

@Composable
fun DriverCard(driver: F1Driver, isFavorite: Boolean, onToggleFavorite: () -> Unit) {
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
                // Safely render potentially null strings
                Text(
                    text = "${driver.driverNumber ?: "N/A"} | ${driver.fullName ?: "Unknown Driver"}",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = driver.teamName ?: "Unknown Team",
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.secondary
                )
            }

            IconButton(onClick = onToggleFavorite) {
                Icon(
                    imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                    contentDescription = "Favorite",
                    tint = if (isFavorite) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}