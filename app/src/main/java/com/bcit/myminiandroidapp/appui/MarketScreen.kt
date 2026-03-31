package com.bcit.myminiandroidapp.appui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

@SuppressLint("DefaultLocale")
@Composable
fun MarketScreen(state: PortfolioState) {
    // We need a coroutine scope to trigger the API call manually from a button click
    val coroutineScope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Live Market", fontSize = 30.sp, fontWeight = FontWeight.Bold)

            // Refresh Button to call the API again manually
            IconButton(onClick = {
                coroutineScope.launch { state.fetchMarketPrices() }
            }) {
                Icon(Icons.Default.Refresh, contentDescription = "Refresh API")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (state.isApiLoading.value) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            // Display the data we fetched from Retrofit
            LazyColumn {
                // Convert Map to List for the LazyColumn
                val marketList = state.marketPrices.toList()

                items(marketList.size) { index ->
                    val (symbol, price) = marketList[index]

                    Card(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = symbol, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                            Text(text = "$${String.format("%.2f", price)}", fontSize = 20.sp)
                        }
                    }
                }
            }
        }
    }
}