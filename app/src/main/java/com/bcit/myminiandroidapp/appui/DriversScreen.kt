package com.bcit.myminiandroidapp.appui

import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
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
import androidx.compose.ui.res.stringResource
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.background
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.bcit.myminiandroidapp.R
import com.bcit.myminiandroidapp.data.remote.F1Driver

@Composable
fun DriversScreen() {
    val f1State: F1State = viewModel(
        LocalActivity.current as ComponentActivity
    )

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        Text(
            stringResource(id = R.string.drivers_title),
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            stringResource(id = R.string.tap_heart),
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.secondary
        )
        Spacer(modifier = Modifier.height(16.dp))

        if (f1State.isLoading.value) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else if (f1State.errorMessage.value != null) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(f1State.errorMessage.value!!, color = MaterialTheme.colorScheme.error)
            }
        } else {
            LazyColumn {
                items(f1State.apiDrivers.size) { index ->
                    val driver = f1State.apiDrivers[index]

                    // Safely check if driver exists in DB
                    val isFavorite =
                        f1State.favoriteDrivers.any { it.driverNumber == driver.driverNumber }

                    DriverCard(
                        driver = driver,
                        isFavorite = isFavorite,
                        onToggleFavorite = { f1State.toggleFavorite(driver) }
                    )
                }
            }
        }
    }
}

@Composable
fun DriverCard(driver: F1Driver, isFavorite: Boolean, onToggleFavorite: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (driver.headshotUrl != null) {
                    AsyncImage(
                        model = driver.headshotUrl,
                        contentDescription = "Driver Headshot",
                        modifier = Modifier
                            .size(50.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.surfaceVariant)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                }
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