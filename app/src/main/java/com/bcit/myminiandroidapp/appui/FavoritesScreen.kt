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
import androidx.compose.ui.res.stringResource
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch
import com.bcit.myminiandroidapp.R
import com.bcit.myminiandroidapp.data.remote.F1Driver

@Composable
fun FavoritesScreen(state: F1State) {
    val coroutineScope = rememberCoroutineScope()
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(stringResource(R.string.favorites_title), fontSize = 30.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))

        if (state.favoriteDrivers.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(stringResource(R.string.no_favorites), color = MaterialTheme.colorScheme.secondary)
            }
        } else {
            LazyColumn {
                items(state.favoriteDrivers.size) { index ->
                    val favoriteDriver = state.favoriteDrivers[index]
                    val apiDriver = state.apiDrivers.find { it.driverNumber == favoriteDriver.driverNumber }

                    DriverCard(
                        driver = F1Driver(
                            driverNumber = favoriteDriver.driverNumber,
                            fullName = favoriteDriver.fullName,
                            teamName = favoriteDriver.teamName,
                            headshotUrl = apiDriver?.headshotUrl
                        ),
                        isFavorite = true,
                        onToggleFavorite = {
                            coroutineScope.launch {
                                state.toggleFavorite(F1Driver(
                                    driverNumber = favoriteDriver.driverNumber,
                                    fullName = favoriteDriver.fullName,
                                    teamName = favoriteDriver.teamName,
                                    headshotUrl = null
                                ))
                            }
                        }
                    )
                }
            }
        }
    }
}