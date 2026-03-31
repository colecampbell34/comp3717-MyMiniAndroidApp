package com.bcit.myminiandroidapp.appui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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
fun MainContent(portfolioState: PortfolioState) {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = { MyBottomNav(navController) }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = "portfolio",
            modifier = Modifier.padding(padding)
        ) {
            composable(route = "portfolio") {
                PortfolioScreen(portfolioState)
            }
            composable(route = "market") {
                MarketScreen()
            }
        }
    }
}

@Composable
fun MyBottomNav(navController: NavController) {
    val navItems = listOf(
        NavItem(Icons.Default.Home, navRoute = "portfolio", title = "Portfolio"),
        NavItem(Icons.Default.ShoppingCart, navRoute = "market", title = "Market")
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