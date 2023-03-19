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
package com.flexcode.wedate.auth.presentation.interests_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.flexcode.wedate.auth.data.local.datastore.AuthDataStore
import com.flexcode.wedate.common.BaseViewModel
import com.flexcode.wedate.common.data.LogService
import com.flexcode.wedate.common.navigation.SEARCHING_FOR_SCREEN
import com.flexcode.wedate.common.snackbar.SnackBarManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class InterestsViewModel @Inject constructor(
    logService: LogService,
    private val dataStore: AuthDataStore
) : BaseViewModel(logService) {

    fun onContinueClicked(openScreen: (String) -> Unit) {
        if (_selectedInterestsOption.value == "[Women]") {
            SnackBarManager.showError("Please select your interest")
            return
        }
        launchCatching { openScreen(SEARCHING_FOR_SCREEN) }
    }

    private val _selectedInterestsOption = mutableStateOf("[Women]")
    val selectedInterestsOption: State<String> = _selectedInterestsOption
    fun setSelectedInterestsOption(value: String) {
        _selectedInterestsOption.value = value
        saveInterestedIn(selectedInterestsOption.value)
    }

    private fun saveInterestedIn(selectedInterestsOption: String) {
        viewModelScope.launch {
            dataStore.saveInterestIn(selectedInterestsOption)
        }
    }
}
