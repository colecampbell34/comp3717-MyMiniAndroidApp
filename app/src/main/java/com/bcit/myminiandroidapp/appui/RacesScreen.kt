package com.bcit.myminiandroidapp.appui

import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bcit.myminiandroidapp.R
import com.bcit.myminiandroidapp.data.remote.F1Session

@Composable
fun RacesScreen(navController: NavController) {
    val f1State: F1State = viewModel(
        LocalActivity.current as ComponentActivity
    )

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        Text(stringResource(R.string.races_title), fontSize = 30.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))

        YearSelector(
            selectedYear = f1State.selectedYear.value,
            onYearSelected = { year ->
                f1State.changeYear(year)
            }
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
        } else if (f1State.apiSessions.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    "No session data found for ${f1State.selectedYear.value}.",
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(f1State.apiSessions.size) { index ->
                    val session = f1State.apiSessions[index]
                    SessionCard(
                        session = session,
                        onClick = {
                            if (session.sessionKey != null) {
                                navController.navigate("session_details/${session.sessionKey}")
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun YearSelector(selectedYear: Int, onYearSelected: (Int) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)
    ) {
        val years = listOf(2024, 2025, 2026)
        years.forEach { year ->
            val isSelected = year == selectedYear
            val buttonColors = if (isSelected) {
                ButtonDefaults.buttonColors()
            } else {
                ButtonDefaults.outlinedButtonColors()
            }

            Button(
                onClick = { onYearSelected(year) },
                colors = buttonColors,
                border = if (!isSelected) ButtonDefaults.outlinedButtonBorder else null
            ) {
                Text("$year")
            }
        }
    }
}

@Composable
fun SessionCard(session: F1Session, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.Flag,
                contentDescription = "Race",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(40.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                val title = session.circuitShortName ?: "Track"
                val subtitle = "${session.sessionName ?: "Session"} • ${session.year ?: ""}"
                Text(text = title, style = MaterialTheme.typography.titleLarge)
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}