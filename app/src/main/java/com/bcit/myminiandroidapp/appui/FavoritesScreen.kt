package com.bcit.myminiandroidapp.appui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bcit.myminiandroidapp.data.remote.F1Driver

@Composable
fun FavoritesScreen(state: F1State) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Favorite Drivers", fontSize = 30.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))

        if (state.favoriteDrivers.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("You haven't added any favorite drivers yet!", color = MaterialTheme.colorScheme.secondary)
            }
        } else {
            LazyColumn {
                items(state.favoriteDrivers.size) { index ->
                    val favoriteDriver = state.favoriteDrivers[index]

                    DriverCard(
                        driver = F1Driver(
                            driverNumber = favoriteDriver.driverNumber,
                            fullName = favoriteDriver.fullName,
                            teamName = favoriteDriver.teamName
                        ),
                        isFavorite = true,
                        onToggleFavorite = {
                            state.toggleFavorite(F1Driver(
                                driverNumber = favoriteDriver.driverNumber,
                                fullName = favoriteDriver.fullName,
                                teamName = favoriteDriver.teamName
                            ))
                        }
                    )
                }
            }
        }
    }
}