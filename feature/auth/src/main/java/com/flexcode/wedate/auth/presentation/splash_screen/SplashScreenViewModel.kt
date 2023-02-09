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

import androidx.compose.runtime.mutableStateOf
import com.flexcode.wedate.auth.domain.repository.AuthRepository
import com.flexcode.wedate.common.BaseViewModel
import com.flexcode.wedate.common.data.LogService
import com.flexcode.wedate.common.navigation.LOGIN_SCREEN
import com.flexcode.wedate.common.navigation.SPLASH_SCREEN
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    logService: LogService
) : BaseViewModel(logService) {
    val showError = mutableStateOf(false)

    fun onAppStart(openAndPopUp: (String, String) -> Unit) {
        showError.value = false
        if (authRepository.hasUser) openAndPopUp(LOGIN_SCREEN, SPLASH_SCREEN)
        else openAndPopUp(LOGIN_SCREEN, SPLASH_SCREEN)
    }
}
