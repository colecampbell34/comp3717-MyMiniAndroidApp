package com.bcit.myminiandroidapp.appui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bcit.myminiandroidapp.data.remote.F1Session

@Composable
fun RacesScreen(state: F1State) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("F1 Race Calendar", fontSize = 30.sp, fontWeight = FontWeight.Bold)
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
                items(state.apiSessions.size) { index ->
                    SessionCard(session = state.apiSessions[index])
                }
            }
        }
    }
}

@Composable
fun SessionCard(session: F1Session) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Using Elvis Operator `?:` to provide a fallback if the API returns null
            Text(text = session.meetingName ?: "Unknown Meeting", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Text(
                text = "${session.sessionName ?: "Unknown Session"} • ${session.countryName ?: "Unknown Country"}",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.secondary
            )
        }
    }
}