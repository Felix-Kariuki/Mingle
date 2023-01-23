package com.flexcode.wedatecompose

import android.content.res.Resources
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.navigation.NavHostController
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.testing.TestNavHostController
import com.flexcode.wedate.common.navigation.SPLASH_SCREEN
import com.flexcode.wedate.common.navigation.WeDateAppState
import com.flexcode.wedate.common.navigation.WeDateNavGraph
import com.flexcode.wedate.common.snackbar.SnackBarManager
import kotlinx.coroutines.CoroutineScope
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class NavigationTest {


    @get:Rule
    val composeTestRule = createComposeRule()
    lateinit var navController: TestNavHostController

    @OptIn(ExperimentalMaterialApi::class)
    @Before
    fun setUpNavHost() {
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())

        }
    }

    // Unit test
    @Test
    fun appNavHost_verifyStartDestination() {
        composeTestRule
            .onNodeWithContentDescription("Splash Screen")
            .assertIsDisplayed()
    }
}


