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
package com.flexcode.wedate.auth.presentation.splash_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.flexcode.wedate.auth.presentation.profile_images_screen.ProfileImageState
import com.flexcode.wedate.common.R
import com.flexcode.wedate.common.composables.LottieAnimationLove
import com.flexcode.wedate.common.navigation.PROFILE_IMAGES_SCREEN
import com.flexcode.wedate.common.navigation.SPLASH_SCREEN
import com.flexcode.wedate.common.utils.Constants
import kotlinx.coroutines.delay
import timber.log.Timber

@Composable
fun SplashScreen(
    openAndPopUp: (String, String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SplashScreenViewModel = hiltViewModel()
) {
    val profileImagesState by viewModel.state

    Column(
        modifier = modifier
            .fillMaxSize()
            .testTag(SPLASH_SCREEN),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (viewModel.showError.value) {
            Text(text = stringResource(R.string.generic_error))
            /*BasicButton(text = R.string.try_again, modifier = Modifier.basicButton()) {
                viewModel.onAppStart(openAndPopUp)
            }*/
        } else {
            LottieAnimationLove()
        }
    }

    LaunchedEffect(key1 = true) {
        delay(Constants.SPLASH_TIMEOUT)
        viewModel.onAppStart(openAndPopUp)
        /**
         * do this check at home screen when done here means impact on app startup time
         */
        /*if (viewModel.hasUser()) {
            viewModel.getUserDetails()
            delay(Constants.SPLASH_TIMEOUT)
            checkNoProfileImageAvailablee(profileImagesState, openAndPopUp, viewModel)
        } else {
            delay(Constants.SPLASH_TIMEOUT)
            viewModel.onAppStart(openAndPopUp)
        }*/
    }
}
fun checkNoProfileImageAvailablee(
    state: ProfileImageState,
    openAndPopUp: (String, String) -> Unit,
    viewModel: SplashScreenViewModel
) {
    if (state.userDetails?.profileImage?.profileImage1 == "" ||
        state.userDetails?.profileImage?.profileImage2 == ""
    ) {
        Timber.i("IMAGES:: True")
        openAndPopUp(PROFILE_IMAGES_SCREEN, SPLASH_SCREEN)
    } else {
        viewModel.onAppStart(openAndPopUp)
    }
}
