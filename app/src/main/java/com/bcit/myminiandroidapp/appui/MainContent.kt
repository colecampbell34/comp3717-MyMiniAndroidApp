package com.bcit.myminiandroidapp.appui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

data class NavItem(val icon: ImageVector, val navRoute: String, val title: String)

@Composable
fun MainContent(f1State: F1State) {
    val navController = rememberNavController()

    // Fetch API data when app opens
    LaunchedEffect(Unit) {
        f1State.fetchApiData()
    }

    Scaffold(
        bottomBar = { MyBottomNav(navController) }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = "races",
            modifier = Modifier.padding(padding)
        ) {
            composable(route = "races") {
                RacesScreen(f1State)
            }
            composable(route = "drivers") {
                DriversScreen(f1State)
            }
        }
    }
}

@Composable
fun MyBottomNav(navController: NavController) {
    val navItems = listOf(
        NavItem(Icons.Default.DateRange, navRoute = "races", title = "Races"),
        NavItem(Icons.Default.Person, navRoute = "drivers", title = "Drivers")
    )

    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        navItems.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.navRoute,
                onClick = { navController.navigate(item.navRoute) },
                icon = { Icon(item.icon, contentDescription = item.title) },
                label = { Text(item.title) }
            )
        }
    }
}