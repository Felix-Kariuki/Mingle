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
package com.flexcode.wedate.matches.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.flexcode.wedate.common.BaseViewModel
import com.flexcode.wedate.common.data.LogService
import com.flexcode.wedate.common.utils.Resource
import com.flexcode.wedatecompose.network.domain.use_cases.matches.MatchesUseCaseContainer
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch
import timber.log.Timber

@HiltViewModel
class MatchesViewModel @Inject constructor(
    logService: LogService,
    private val useCases: MatchesUseCaseContainer
) : BaseViewModel(logService) {
    var state = mutableStateOf(MatchesState())
        private set

    private val searchValue
        get() = state.value.searchValue

    fun onSearchValueChange(value: String) {
        state.value = state.value.copy(searchValue = value)
    }

    init {
        getMatches()
        getChatProfiles()
    }

    private fun getMatches() {
        viewModelScope.launch {
            useCases.getAllUserMatchesUseCase.invoke().collect { result ->
                when (result) {
                    is Resource.Success -> {
                        Timber.i("MATCHES:: ${result.data}")
                        state.value = result.data?.let { state.value.copy(matches = it) }!!
                    }

                    is Resource.Loading -> {}
                    is Resource.Error -> {
                        Timber.e("MATCHES ERROR::: ${result.message}")
                    }
                }
            }
        }
    }

    private fun getChatProfiles() {
        viewModelScope.launch {
            useCases.getChatProfilesUseCase.invoke().collect { result ->
                when (result) {
                    is Resource.Success -> {
                        state.value = result.data?.let { state.value.copy(chatProfiles = it) }!!
                    }

                    is Resource.Loading -> {}
                    is Resource.Error -> {
                        Timber.e("CHAT PROFILE ERROR::: ${result.message}")
                    }
                }
            }
        }
    }
}
