package com.flexcode.wedate.common.navigation

import android.content.res.Resources
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.flexcode.wedate.auth.presentation.identity_screen.IdentityScreen
import com.flexcode.wedate.auth.presentation.interests_screen.InterestsScreen
import com.flexcode.wedate.auth.presentation.login.LoginScreen
import com.flexcode.wedate.auth.presentation.profile_images_screen.ProfileImagesScreen
import com.flexcode.wedate.auth.presentation.register.RegisterScreen
import com.flexcode.wedate.auth.presentation.searching_for_screen.SearchingForScreen
import com.flexcode.wedate.auth.presentation.splash_screen.SplashScreen
import com.flexcode.wedate.common.snackbar.SnackBarManager
import com.flexcode.wedate.common.theme.WedateComposeTheme
import com.flexcode.wedatecompose.HomeContentScreen
import kotlinx.coroutines.CoroutineScope

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun WeDateApp() {
    WedateComposeTheme {
        Surface(
            color = MaterialTheme.colors.background
        ) {
            val appState = rememberAppState()

            Scaffold(
                snackbarHost = {
                    SnackbarHost(
                        hostState = it,
                        modifier = Modifier.padding(8.dp),
                        snackbar = {
                            Snackbar(
                                snackbarData = it,
                                contentColor = MaterialTheme.colors.onPrimary
                            )
                        }
                    )
                },
                scaffoldState = appState.scaffoldState
            ) { padddingValues ->
                NavHost(
                    navController = appState.navController,
                    startDestination = SPLASH_SCREEN,
                    modifier = Modifier.padding(padddingValues)
                ) {
                    WeDateNavGraph(appState)
                }
            }
        }
    }
}

@ExperimentalMaterialApi
fun NavGraphBuilder.WeDateNavGraph(appState: WeDateAppState) {
    composable(route = com.flexcode.wedate.common.navigation.SPLASH_SCREEN) {
        SplashScreen(
            openAndPopUp = { route, popUp ->
                appState.navigateAndPopUp(route = route, popUp = popUp)
            })
    }

    composable(route = com.flexcode.wedate.common.navigation.LOGIN_SCREEN,
    arguments = listOf()
    ) {
        LoginScreen(openAndPopUp = { route, popup ->
            appState.navigateAndPopUp(route = route, popUp = popup)
        }, openScreen = { route ->
            appState.navigate(route = route)
        })
    }

    composable(route = com.flexcode.wedate.common.navigation.REGISTER_SCREEN) {
        RegisterScreen(openAndPopUp = { route, popUp ->
            appState.navigateAndPopUp(route = route, popUp = popUp)
        }, openScreen = { route ->
            appState.navigate(route = route)
        })
    }

    composable(route = com.flexcode.wedate.common.navigation.IDENTITY_SCREEN) {
        IdentityScreen(openScreen = { route ->
            appState.navigate(route = route)
        })
    }

    composable(route = com.flexcode.wedate.common.navigation.INTERESTS_SCREEN) {
        InterestsScreen(openScreen = {route-> appState.navigate(route)})
    }

    composable(route = com.flexcode.wedate.common.navigation.SEARCHING_FOR_SCREEN) {
        SearchingForScreen(openScreen = {route-> appState.navigate(route)})
    }

    composable(route = com.flexcode.wedate.common.navigation.PROFILE_IMAGES_SCREEN) {
        ProfileImagesScreen(openAndPopUp = {route,popUp-> appState.navigateAndPopUp(route,popUp)})
    }

    composable(route = com.flexcode.wedate.common.navigation.FORGOT_PASSWORD_SCREEN) {

    }

    composable(route = com.flexcode.wedate.common.navigation.HOME_SCREEN_CONTENT) {
        HomeContentScreen()
    }
}


@Composable
fun rememberAppState(
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    navController: NavHostController = rememberNavController(),
    snackbarManager: SnackBarManager = SnackBarManager,
    resources: Resources = resources(),
    coroutineScope: CoroutineScope = rememberCoroutineScope()
) =
    remember(scaffoldState, navController, snackbarManager, resources, coroutineScope) {
        WeDateAppState(
            scaffoldState,
            navController,
            snackbarManager,
            resources,
            coroutineScope
        )
    }

@Composable
@ReadOnlyComposable
fun resources(): Resources {
    LocalConfiguration.current
    return LocalContext.current.resources
}