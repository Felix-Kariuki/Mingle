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

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.flexcode.wedate.common.navigation.*
import timber.log.Timber

@Composable
fun HomeContentScreen() {
    val navController = rememberNavController()
    val bottomBarState = rememberSaveable { (mutableStateOf(true)) }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    when (navBackStackEntry?.destination?.route) {
        PROFILE_DETAILS_SCREEN -> {
            bottomBarState.value = false
        }
        EDIT_PROFILE_SCREEN -> {
            bottomBarState.value = false
        }
        SETTINGS_SCREEN -> {
            bottomBarState.value = false
        }
        LOVE_CALCULATOR_SCREEN -> {
            bottomBarState.value = false
        }
        CHATS_SCREEN -> {
            bottomBarState.value = false
        }
        else -> {
            bottomBarState.value = true
        }
    }
    Scaffold(
        bottomBar = {
            BottomNavigation(navController = navController, bottomBarState)
        }
    ) {
        Timber.d("$it")
        NavigationGraph(navController)
    }
}
