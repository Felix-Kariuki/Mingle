package com.flexcode.wedate.auth

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.flexcode.wedate.common.navigation.SPLASH_SCREEN
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ScreensTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun verifySplashScreenIsDisplayed(){
        composeTestRule
            .onNodeWithTag(SPLASH_SCREEN)
            .assertIsDisplayed()
    }

}