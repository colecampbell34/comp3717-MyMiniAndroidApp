package com.bcit.myminiandroidapp.appui

import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bcit.myminiandroidapp.R
import com.bcit.myminiandroidapp.data.remote.F1Driver

@Composable
fun FavoritesScreen() {
    val f1State: F1State = viewModel(
        LocalActivity.current as ComponentActivity
    )

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        Text(
            stringResource(R.string.favorites_title),
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))

        if (f1State.favoriteDrivers.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    stringResource(R.string.no_favorites),
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        } else {
            LazyColumn {
                items(f1State.favoriteDrivers.size) { index ->
                    val favoriteDriver = f1State.favoriteDrivers[index]
                    val apiDriver =
                        f1State.apiDrivers.find { it.driverNumber == favoriteDriver.driverNumber }

                    DriverCard(
                        driver = F1Driver(
                            driverNumber = favoriteDriver.driverNumber,
                            fullName = favoriteDriver.fullName,
                            teamName = favoriteDriver.teamName,
                            headshotUrl = apiDriver?.headshotUrl
                        ),
                        isFavorite = true,
                        onToggleFavorite = {
                            f1State.toggleFavorite(
                                F1Driver(
                                    driverNumber = favoriteDriver.driverNumber,
                                    fullName = favoriteDriver.fullName,
                                    teamName = favoriteDriver.teamName,
                                    headshotUrl = null
                                )
                            )
                        }
                    )
                }
            }
        }
    }
}