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
package com.flexcode.wedate.auth.presentation.identity_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.flexcode.wedate.auth.data.local.datastore.AuthDataStore
import com.flexcode.wedate.auth.data.models.User
import com.flexcode.wedate.common.BaseViewModel
import com.flexcode.wedate.common.R.string as AppText
import com.flexcode.wedate.common.data.LogService
import com.flexcode.wedate.common.extestions.isDateValid
import com.flexcode.wedate.common.extestions.isMonthValid
import com.flexcode.wedate.common.extestions.isYearValid
import com.flexcode.wedate.common.navigation.INTERESTS_SCREEN
import com.flexcode.wedate.common.snackbar.SnackBarManager
import dagger.hilt.android.lifecycle.HiltViewModel
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class IdentifyScreenViewModel @Inject constructor(
    logService: LogService,
    private val dataStore: AuthDataStore
) : BaseViewModel(logService) {

    val user = mutableStateOf(User())

    var state = mutableStateOf(IdentityState())
        private set

    private val dd
        get() = state.value.dd

    private val mm
        get() = state.value.mm

    private val yy
        get() = state.value.yy

    private val _selectedGenderOption = mutableStateOf("[Male]")
    val selectedGenderOption: State<String> = _selectedGenderOption
    fun setSelectedGenderOption(value: String) {
        _selectedGenderOption.value = value
        saveGender(selectedGenderOption.value)
    }

    private fun saveGender(selectedGenderOption: String) {
        viewModelScope.launch {
            dataStore.saveGender(selectedGenderOption)
        }
    }

    fun onContinueClicked(openScreen: (String) -> Unit) {
        if (!dd.isDateValid()) {
            SnackBarManager.showMessage(AppText.dd_error)
            return
        }
        if (!mm.isMonthValid()) {
            SnackBarManager.showMessage(AppText.mm_error)
            return
        }
        if (!yy.isYearValid()) {
            SnackBarManager.showMessage(AppText.yy_error)
            return
        }

        val yob =
            user.value.dateOfBirth + "/" + user.value.monthOfBirth + "/" + user.value.yearOfBirth

        calculateAge(yob, openScreen)
    }

    fun onDateOfBirthChange(newValue: String) {
        state.value = state.value.copy(dd = newValue)
        user.value = user.value.copy(dateOfBirth = newValue)
    }

    fun onYearOfBirthChange(newValue: String) {
        state.value = state.value.copy(yy = newValue)
        user.value = user.value.copy(yearOfBirth = newValue)
    }

    fun onMonthOfBirthChange(newValue: String) {
        state.value = state.value.copy(mm = newValue)
        user.value = user.value.copy(monthOfBirth = newValue)
    }

    private fun calculateAge(yob: String, openScreen: (String) -> Unit) {
        val yearOfBirth = "$yob 00:00"
        val today = Date()
        val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm")
        val formattedYearOfBirth = sdf.parse(yearOfBirth)
        val years = (today.time - formattedYearOfBirth.time) / 31536000000
        if (years.toInt() >= 18) {
            if (_selectedGenderOption.value == "[Male]") {
                SnackBarManager.showMessage(AppText.gender_error)
                return
            }
            launchCatching {
                dataStore.saveYearOfBirth(year = yob)
                dataStore.saveAge(age = years.toString())
                openScreen(INTERESTS_SCREEN)
            }
        } else {
            // Do not show error at this point check age before actual registration then
            // show error "Account creation failed, you cannot be less than 18 years"
            SnackBarManager.showError("You are below the age of 18")
        }
    }
}
