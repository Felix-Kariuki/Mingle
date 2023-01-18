package com.flexcode.wedatecompose

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import timber.log.Timber

@Composable
fun HomeContentScreen() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomNavigation(navController = navController) },
    ) {
        Timber.d("$it")
       NavigationGraph(navController)

    }


}