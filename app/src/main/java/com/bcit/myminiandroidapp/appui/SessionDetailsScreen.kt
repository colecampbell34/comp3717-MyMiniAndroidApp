package com.bcit.myminiandroidapp.appui

import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.bcit.myminiandroidapp.data.remote.DriverPosition
import com.bcit.myminiandroidapp.data.remote.F1Session
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SessionDetailsScreen(sessionKey: Int?, navController: NavController) {
    val f1State: F1State = viewModel(
        LocalActivity.current as ComponentActivity
    )

    val session = f1State.apiSessions.find { it.sessionKey == sessionKey }

    LaunchedEffect(sessionKey) {
        if (sessionKey != null) {
            f1State.fetchSessionPositions(sessionKey)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = session?.sessionName ?: "Results") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            )
        }
    ) { padding ->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(padding)) {
            session?.let { SessionInfoHeader(it) }

            if (f1State.isDetailsLoading.value) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else if (f1State.errorMessage.value != null) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(f1State.errorMessage.value!!, color = MaterialTheme.colorScheme.error)
                }
            } else if (f1State.sessionPositions.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        "No results available for this session.",
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(f1State.sessionPositions.size) { index ->
                        val positionData = f1State.sessionPositions[index]
                        val driverDetails =
                            f1State.apiDrivers.find { it.driverNumber == positionData.driverNumber }
                        PositionRow(
                            position = positionData,
                            driverName = driverDetails?.fullName,
                            teamName = driverDetails?.teamName,
                            headshotUrl = driverDetails?.headshotUrl
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SessionInfoHeader(session: F1Session) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(16.dp)
    ) {
        val date = session.dateStart?.let {
            LocalDate.parse(it, DateTimeFormatter.ISO_DATE_TIME)
                .format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG))
        } ?: "Date unknown"

        Text(
            text = session.meetingName ?: "Grand Prix",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "${session.circuitShortName ?: "Track"} • $date",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.secondary
        )
    }
}


@Composable
fun PositionRow(
    position: DriverPosition,
    driverName: String?,
    teamName: String?,
    headshotUrl: String?
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center
            ) {

                Text(
                    text = "${position.position}",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            if (headshotUrl != null) {
                AsyncImage(
                    model = headshotUrl,
                    contentDescription = "Driver Headshot",
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                )
                Spacer(modifier = Modifier.width(16.dp))
            }
            Column {
                Text(
                    text = driverName ?: "Unknown Driver",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = teamName ?: "Unknown Team",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.secondary
                )

            }
        }
    }
}