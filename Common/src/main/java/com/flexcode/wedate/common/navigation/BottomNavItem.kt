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
package com.flexcode.wedate.common.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.ChatBubble
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.HolidayVillage
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PersonOutline
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(var icon: ImageVector, var screen_route: String) {
    object Explore : BottomNavItem(Icons.Default.Explore, EXPLORE_SCREEN)
    object Home : BottomNavItem(Icons.Default.HolidayVillage, HOME_SCREEN)
    object Admirers : BottomNavItem(Icons.Default.Favorite, ADMIRERS_SCREEN)
    object Matches : BottomNavItem(Icons.Default.Chat, MATCHES_SCREEN)
    object Account : BottomNavItem(Icons.Default.PersonOutline, ACCOUNT_SCREEN)
}
