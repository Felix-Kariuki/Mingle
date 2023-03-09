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

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.flexcode.wedate.account.AccountScreen
import com.flexcode.wedate.admirers.presentation.AdmirersScreen
import com.flexcode.wedate.chatsscreen.presentation.ChatsScreen
import com.flexcode.wedate.common.navigation.*
import com.flexcode.wedate.common.theme.purple
import com.flexcode.wedate.home.presentation.HomeScreen
import com.flexcode.wedate.lovecalculator.presentation.LoveCalculatorScreen
import com.flexcode.wedate.maps.MapsScreen
import com.flexcode.wedate.matches.presentation.MatchesScreen
import com.flexcode.wedate.profiledetails.ProfileDetailsScreen
import com.flexcode.wedate.profileedit.presentation.EditProfileScreen
import com.flexcode.wedate.settings.SettingsScreen

@Composable
fun NavigationGraph(navController: NavHostController) {
    NavHost(navController, startDestination = BottomNavItem.Home.screen_route) {
        composable(BottomNavItem.Home.screen_route) {
            HomeScreen()
        }
        composable(BottomNavItem.Admirers.screen_route) {
            AdmirersScreen(
                navigateToAdmirersMaps = {
                    navController.navigate(route = MAPS_SCREEN)
                }
            )
        }
        composable(BottomNavItem.Matches.screen_route) {
            MatchesScreen(
                navigateToChats = { userId ->
                    navController.navigate(
                        route = "$CHATS_SCREEN/$userId"
                    )
                }
            )
        }
        composable(
            route = "$CHATS_SCREEN/{data}",
            arguments = listOf(
                navArgument("data") { type = NavType.StringType }
            )
        ) { navBackStackEntry ->
            ChatsScreen(
                navigateToMatchScreen = {
                    navController.popBackStack()
                },
                data = navBackStackEntry.arguments?.getString("data")
            )
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
                navController.popBackStack()
            })
        }
        composable(route = SETTINGS_SCREEN) {
            SettingsScreen(
                openAndPopUp = {
                    navController.popBackStack()
                }
            )
        }
        composable(route = PROFILE_DETAILS_SCREEN) {
            ProfileDetailsScreen(
                navigateToEditProfile = {
                    navController.navigate(route = EDIT_PROFILE_SCREEN)
                },
                navigateToAccountScreen = {
                    navController.popBackStack()
                }
            )
        }
        composable(route = EDIT_PROFILE_SCREEN) {
            EditProfileScreen(
                navigateToProfileDetails = {
                    navController.popBackStack()
                }
            )
        }
        composable(route = MAPS_SCREEN) {
            MapsScreen(
                navigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}

@Composable
fun BottomNavigation(
    navController: NavController,
    bottomBarState: MutableState<Boolean>
) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Admirers,
        BottomNavItem.Matches,
        BottomNavItem.Account
    )

    AnimatedVisibility(
        visible = bottomBarState.value,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it }),
        content = {
            androidx.compose.material.BottomNavigation(
                backgroundColor = MaterialTheme.colors.background
            ) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route
                items.forEach { item ->
                    BottomNavigationItem(
                        icon = { Icon(imageVector = item.icon, contentDescription = "") },
                        selectedContentColor = purple,
                        unselectedContentColor = Color.Black.copy(0.8f),
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
    )
}
