package com.bcit.myminiandroidapp.appui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

data class NavItem(val icon: ImageVector, val navRoute: String, val title: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainContent(f1State: F1State) {
    val navController = rememberNavController()

    LaunchedEffect(Unit) {
        f1State.fetchApiData()
    }

    Scaffold(
        bottomBar = { MyBottomNav(navController = navController) }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = "races",
            modifier = Modifier.padding(padding)
        ) {
            composable(route = "races") {
                // Pass NavController so it can navigate from this screen
                RacesScreen(state = f1State, navController = navController)
            }
            composable(route = "drivers") {
                DriversScreen(state = f1State)
            }
            // NEW: Route for the favorites screen
            composable(route = "favorites") {
                FavoritesScreen(state = f1State)
            }
            // NEW: Route for session details, which accepts an argument
            composable(
                route = "session_details/{sessionKey}",
                arguments = listOf(navArgument("sessionKey") { type = NavType.IntType })
            ) { backStackEntry ->
                val sessionKey = backStackEntry.arguments?.getInt("sessionKey")
                SessionDetailsScreen(
                    state = f1State,
                    sessionKey = sessionKey,
                    navController = navController
                )
            }
        }
    }
}


@Composable
fun MyBottomNav(navController: androidx.navigation.NavController) {
    // NEW: Added "Favorites" to the list
    val navItems = listOf(
        NavItem(Icons.Default.DateRange, navRoute = "races", title = "Races"),
        NavItem(Icons.Default.Person, navRoute = "drivers", title = "Drivers"),
        NavItem(Icons.Filled.Favorite, navRoute = "favorites", title = "Favorites")
    )

    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        navItems.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.navRoute,
                onClick = {
                    navController.navigate(item.navRoute) {
                        // Avoid multiple copies of the same destination when re-selecting the same item
                        launchSingleTop = true
                    }
                },
                icon = { Icon(item.icon, contentDescription = item.title) },
                label = { Text(item.title) }
            )
        }
    }
}