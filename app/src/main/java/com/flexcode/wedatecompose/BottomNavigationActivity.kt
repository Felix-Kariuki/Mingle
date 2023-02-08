package com.flexcode.wedatecompose

import androidx.compose.foundation.Image
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
import com.flexcode.wedate.account.AccountScreen
import com.flexcode.wedate.admirers.presentation.AdmirersScreen
import com.flexcode.wedate.common.navigation.ACCOUNT_SCREEN
import com.flexcode.wedate.common.navigation.BottomNavItem
import com.flexcode.wedate.common.navigation.LOVE_CALCULATOR_SCREEN
import com.flexcode.wedate.common.navigation.SETTINGS_SCREEN
import com.flexcode.wedate.common.theme.deepBrown
import com.flexcode.wedate.home.presentation.HomeScreen
import com.flexcode.wedate.lovecalculator.presentation.LoveCalculatorScreen
import com.flexcode.wedate.matches.presentation.MatchesScreen
import com.flexcode.wedate.settings.SettingsScreen

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
            AccountScreen(openScreen = {
                navController.navigate(route = LOVE_CALCULATOR_SCREEN)
            },
            navigateToSettings = {
                navController.navigate(route = SETTINGS_SCREEN)
            })
        }
        composable(route = LOVE_CALCULATOR_SCREEN){
            LoveCalculatorScreen(openAndPopUp = {
                navController.navigate(route = ACCOUNT_SCREEN){
                    popUpTo(ACCOUNT_SCREEN)
                }
            })
        }
        composable(route= SETTINGS_SCREEN){
            SettingsScreen(
                openAndPopUp = {
                    navController.navigate(route = ACCOUNT_SCREEN){
                        popUpTo(ACCOUNT_SCREEN)
                    }
                }
            )
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

    androidx.compose.material.BottomNavigation(
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