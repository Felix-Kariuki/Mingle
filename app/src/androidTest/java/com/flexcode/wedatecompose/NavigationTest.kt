package com.flexcode.wedatecompose

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import androidx.test.runner.AndroidJUnitRunner
import com.flexcode.wedate.auth.presentation.splash_screen.SplashScreen
import com.flexcode.wedate.common.navigation.SPLASH_SCREEN
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

class NavigationTest {


    @get:Rule
    //val composeTestRule = createComposeRule<MainActivity>()
    val composeTestRule = createAndroidComposeRule<MainActivity>()


    @Test
    fun appNavHost_verifyStartDestination() {
        composeTestRule
            .onNodeWithTag(SPLASH_SCREEN)
            .assertIsDisplayed()
    }
}


