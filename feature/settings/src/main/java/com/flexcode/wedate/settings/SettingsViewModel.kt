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
package com.flexcode.wedate.settings

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.flexcode.wedate.auth.domain.repository.AuthRepository
import com.flexcode.wedate.auth.domain.usecase.UseCaseContainer
import com.flexcode.wedate.common.BaseViewModel
import com.flexcode.wedate.common.data.LogService
import com.flexcode.wedate.common.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class SettingsViewModel @Inject constructor(
    logService: LogService,
    private val authRepository: AuthRepository,
    private val useCaseContainer: UseCaseContainer
) : BaseViewModel(logService) {

    var state = mutableStateOf(SettingsState())
        private set

    fun signOut() {
        authRepository.signOut()
    }

    init {
        getUserDetails()
    }

    private fun getUserDetails() {
        viewModelScope.launch {
            useCaseContainer.getUserDetailsUseCase().collect { result ->
                when (result) {
                    is Resource.Success -> {
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
}
