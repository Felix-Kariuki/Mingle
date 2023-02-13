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
package com.flexcode.wedate.auth.presentation.searching_for_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.flexcode.wedate.auth.data.local.datastore.AuthDataStore
import com.flexcode.wedate.auth.domain.usecase.UseCaseContainer
import com.flexcode.wedate.auth.presentation.profile_images_screen.ProfileImageState
import com.flexcode.wedate.common.BaseViewModel
import com.flexcode.wedate.common.data.LogService
import com.flexcode.wedate.common.navigation.PROFILE_IMAGES_SCREEN
import com.flexcode.wedate.common.navigation.SEARCHING_FOR_SCREEN
import com.flexcode.wedate.common.snackbar.SnackBarManager
import com.flexcode.wedate.common.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@HiltViewModel
class SearchingForViewModel @Inject constructor(
    logService: LogService,
    private val dataStore: AuthDataStore,
    private val useCase: UseCaseContainer
) : BaseViewModel(logService) {

    var state = mutableStateOf(SearchingUiState())
        private set

    var email = ""
    var firstName = ""
    var phone = ""
    var gender = ""
    var interest = ""
    var yearBirth = ""
    var password = ""
    var age = ""

    init {
        viewModelScope.launch {
            dataStore.getUserGender.collect {
                gender = it.substring(1, it.length - 1)
            }
        }
        viewModelScope.launch {
            dataStore.getUserInterest.collect {
                interest = it.substring(1, it.length - 1)
            }
        }
        viewModelScope.launch {
            dataStore.getYearOfBirth.collect {
                yearBirth = it.substring(1, it.length - 1)
            }
        }
        viewModelScope.launch {
            dataStore.getUserName.collect {
                firstName = it.substring(1, it.length - 1)
            }
        }
        viewModelScope.launch {
            dataStore.getUserEmail.collect {
                email = it.substring(1, it.length - 1)
            }
        }
        viewModelScope.launch {
            dataStore.getUserPhone.collect {
                phone = it.substring(1, it.length - 1)
            }
        }
        viewModelScope.launch {
            dataStore.getUserPwd.collect {
                password = it.substring(1, it.length - 1)
            }
        }
        viewModelScope.launch {
            dataStore.getUserAge.collect {
                age = it.substring(1, it.length - 1)
            }
        }
    }

    private val _selectSearchForOption = mutableStateOf("[Relationship]")
    val selectSearchForOption: State<String> = _selectSearchForOption
    fun setSelectSearchForOption(value: String) {
        _selectSearchForOption.value = value
    }

    fun registerUser(openAndPoUp: (String, String) -> Unit) {
        viewModelScope.launch {
            useCase.registerUseCase(
                firstName = firstName,
                phoneNumber = phone,
                email = email,
                password = password,
                dateOfBirth = yearBirth,
                monthOfBirth = "",
                yearOfBirth = "",
                years = age,
                gender = gender,
                interestedIn = interest,
                searchingFor = selectSearchForOption.value

            ).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        state.value = state.value.copy(isLoading = "false")
                        launchCatching {
                            openAndPoUp(PROFILE_IMAGES_SCREEN, SEARCHING_FOR_SCREEN)
                        }
                    }
                    is Resource.Loading -> {
                        state.value = state.value.copy(isLoading = "true")
                    }

                    is Resource.Error -> {
                        state.value = state.value.copy(isLoading = "false")
                        SnackBarManager.showError(result.message.toString())
                    }
                }
            }
        }
    }
}
