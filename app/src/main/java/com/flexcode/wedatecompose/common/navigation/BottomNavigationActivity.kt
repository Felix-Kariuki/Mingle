package com.flexcode.wedatecompose.common.navigation

import androidx.compose.foundation.Image
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.flexcode.wedatecompose.presentation.home.HomeScreen
import com.flexcode.wedatecompose.presentation.home.account.AccountScreen
import com.flexcode.wedatecompose.presentation.home.admirers.AdmirersScreen
import com.flexcode.wedatecompose.presentation.home.matches.MatchesScreen
import com.flexcode.wedatecompose.ui.theme.deepBrown

@Composable
fun NavigationGraph(navController: NavHostController) {
    NavHost(navController, startDestination = BottomNavItem.Home.screen_route) {
        composable(BottomNavItem.Home.screen_route) {
            HomeScreen()
        }
        composable(BottomNavItem.Admirers.screen_route) {
            AdmirersScreen()
        }
        composable(BottomNavItem.Matches.screen_route) {
            MatchesScreen()
        }
        composable(BottomNavItem.Account.screen_route) {
            AccountScreen()
        }
    }
}

@Composable
fun BottomNavigation(
    navController: NavController,
) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Admirers,
        BottomNavItem.Matches,
        BottomNavItem.Account
    )

    BottomNavigation(
        backgroundColor = MaterialTheme.colors.background
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { item ->
            BottomNavigationItem(
                icon = { Image(imageVector = item.icon ,contentDescription = "") },
                selectedContentColor = deepBrown,
                unselectedContentColor = Color.Black.copy(0.4f),
                alwaysShowLabel = true,
                selected = currentRoute == item.screen_route,
                onClick = {
                    navController.navigate(item.screen_route) {
                        navController.graph.startDestinationRoute?.let { screen_route ->
                            popUpTo(screen_route) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}