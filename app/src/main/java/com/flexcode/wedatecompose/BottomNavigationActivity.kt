/*
 * Copyright 2023 Felix Kariuki.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
import com.flexcode.wedate.common.navigation.*
import com.flexcode.wedate.common.theme.deepBrown
import com.flexcode.wedate.home.presentation.HomeScreen
import com.flexcode.wedate.lovecalculator.presentation.LoveCalculatorScreen
import com.flexcode.wedate.matches.presentation.MatchesScreen
import com.flexcode.wedate.profiledetails.ProfileDetailsScreen
import com.flexcode.wedate.profileedit.EditProfileScreen
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
            AccountScreen(
                openScreen = {
                    navController.navigate(route = LOVE_CALCULATOR_SCREEN)
                },
                navigateToSettings = {
                    navController.navigate(route = SETTINGS_SCREEN)
                },
                navigateToProfileDetails = {
                    navController.navigate(route = PROFILE_DETAILS_SCREEN)
                }
            )
        }
        composable(route = LOVE_CALCULATOR_SCREEN) {
            LoveCalculatorScreen(openAndPopUp = {
                navController.navigate(route = ACCOUNT_SCREEN) {
                    popUpTo(ACCOUNT_SCREEN)
                }
            })
        }
        composable(route = SETTINGS_SCREEN) {
            SettingsScreen(
                openAndPopUp = {
                    navController.navigate(route = ACCOUNT_SCREEN) {
                        popUpTo(ACCOUNT_SCREEN)
                    }
                }
            )
        }
        composable(route = PROFILE_DETAILS_SCREEN) {
            ProfileDetailsScreen(
                navigateToEditProfile = {
                    navController.navigate(route = EDIT_PROFILE_SCREEN)
                },
                navigateToAccountScreen = {
                    navController.navigate(route = ACCOUNT_SCREEN) {
                        popUpTo(ACCOUNT_SCREEN)
                    }
                }
            )
        }

        composable(route = EDIT_PROFILE_SCREEN) {
            EditProfileScreen(
                navigateToProfileDetails = {
                    navController.navigate(PROFILE_DETAILS_SCREEN) {
                        popUpTo(PROFILE_DETAILS_SCREEN)
                    }
                }
            )
        }
    }
}

@Composable
fun BottomNavigation(
    navController: NavController
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
                icon = { Image(imageVector = item.icon, contentDescription = "") },
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
