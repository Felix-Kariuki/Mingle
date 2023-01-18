package com.flexcode.wedate.common.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ChatBubble
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(var icon:ImageVector,var screen_route:String){
    object Home: BottomNavItem(Icons.Default.Home, HOME_SCREEN)
    object Admirers: BottomNavItem(Icons.Default.Favorite, ADMIRERS_SCREEN)
    object Matches: BottomNavItem(Icons.Default.ChatBubble, MATCHES_SCREEN)
    object Account: BottomNavItem(Icons.Default.AccountCircle, ACCOUNT_SCREEN)
}
