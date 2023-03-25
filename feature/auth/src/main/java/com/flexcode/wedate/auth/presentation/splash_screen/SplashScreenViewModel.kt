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
import androidx.lifecycle.viewModelScope
import com.flexcode.wedate.auth.domain.repository.AuthRepository
import com.flexcode.wedate.auth.domain.usecase.UseCaseContainer
import com.flexcode.wedate.auth.presentation.profile_images_screen.ProfileImageState
import com.flexcode.wedate.common.BaseViewModel
import com.flexcode.wedate.common.data.LogService
import com.flexcode.wedate.common.navigation.LOGIN_SCREEN
import com.flexcode.wedate.common.navigation.SPLASH_SCREEN
import com.flexcode.wedate.common.utils.Resource
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch
import timber.log.Timber

@HiltViewModel
class SplashScreenViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val useCaseContainer: UseCaseContainer,
    private val auth: FirebaseAuth,
    logService: LogService
) : BaseViewModel(logService) {
    val showError = mutableStateOf(false)

    var state = mutableStateOf(ProfileImageState())
        private set

    init {
        hasUser()
    }

    fun getUserDetails() {
        viewModelScope.launch {
            useCaseContainer.getUserDetailsUseCase(auth.uid!!).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        Timber.i("IMAGES:: ${result.data?.profileImage?.profileImage1}")
                        state.value = state.value.copy(
                            userDetails = result.data
                        )
                    }

                    is Resource.Loading -> {}
                    is Resource.Error -> {}
                }
            }
        }
    }

    fun onAppStart(openAndPopUp: (String, String) -> Unit) {
        showError.value = false
        if (authRepository.hasUser) openAndPopUp(LOGIN_SCREEN, SPLASH_SCREEN)
        else openAndPopUp(LOGIN_SCREEN, SPLASH_SCREEN)
    }

    fun hasUser(): Boolean {
        return authRepository.hasUser
    }
}
