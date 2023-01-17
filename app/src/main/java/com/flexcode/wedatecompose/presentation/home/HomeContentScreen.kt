package com.flexcode.wedatecompose.presentation.home

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.flexcode.wedatecompose.common.navigation.BottomNavigation
import com.flexcode.wedatecompose.common.navigation.NavigationGraph

@Composable
fun HomeContentScreen() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomNavigation(navController = navController) }
    ) {
        NavigationGraph(navController = navController)
    }
}