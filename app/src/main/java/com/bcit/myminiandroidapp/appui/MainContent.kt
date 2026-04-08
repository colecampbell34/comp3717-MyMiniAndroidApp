package com.bcit.myminiandroidapp.appui

import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
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
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.bcit.myminiandroidapp.R

data class NavItem(val icon: ImageVector, val navRoute: String, val titleRes: Int)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainContent() {
    val f1State: F1State = viewModel(
        LocalActivity.current as ComponentActivity
    )

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
                RacesScreen(navController = navController)
            }
            composable(route = "drivers") {
                DriversScreen()
            }
            composable(route = "favorites") {
                FavoritesScreen()
            }
            composable(
                route = "session_details/{sessionKey}",
                arguments = listOf(navArgument("sessionKey") { type = NavType.IntType })
            ) { backStackEntry ->
                val sessionKey = backStackEntry.arguments?.getInt("sessionKey")
                SessionDetailsScreen(sessionKey = sessionKey, navController = navController)
            }
        }
    }
}


@Composable
fun MyBottomNav(navController: NavController) {
    val navItems = listOf(
        NavItem(Icons.Default.DateRange, navRoute = "races", titleRes = R.string.nav_races),
        NavItem(Icons.Default.Person, navRoute = "drivers", titleRes = R.string.nav_drivers),
        NavItem(Icons.Filled.Favorite, navRoute = "favorites", titleRes = R.string.nav_favorites)
    )

    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        navItems.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.navRoute,
                onClick = {
                    navController.navigate(item.navRoute) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = { Icon(item.icon, contentDescription = stringResource(item.titleRes)) },
                label = { Text(stringResource(item.titleRes)) }
            )
        }
    }
}