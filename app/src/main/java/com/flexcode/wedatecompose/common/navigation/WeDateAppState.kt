package com.flexcode.wedatecompose.common.navigation

import android.content.res.Resources
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Stable
import androidx.navigation.NavHostController
import com.flexcode.wedatecompose.common.snackbar.SnackBarManager
import com.flexcode.wedatecompose.common.snackbar.SnackbarMessage.Companion.toMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch

@Stable
class WeDateAppState(
    val scaffoldState: ScaffoldState,
    val navController: NavHostController,
    private val snackBarManager: SnackBarManager,
    private val resources: Resources,
    coroutineScope: CoroutineScope
) {
    init {
        coroutineScope.launch {
            snackBarManager.snackbarMessages.filterNotNull().collect{
                val text = it.toMessage(resources)
                scaffoldState.snackbarHostState.showSnackbar(text)
            }
        }
    }

    fun popUp() {
        navController.popBackStack()
    }

    fun navigate(route:String){
        navController.navigate(route) {
            launchSingleTop = true
        }
    }

    fun navigateAndPopUp(route: String,popUp:String){
        navController.navigate(route) {
            launchSingleTop = true
            popUpTo(popUp) { inclusive = true }
        }
    }

    fun clearAndNavigate(route: String) {
        navController.navigate(route) {
            launchSingleTop = true
            popUpTo(0) { inclusive = true }
        }
    }
}