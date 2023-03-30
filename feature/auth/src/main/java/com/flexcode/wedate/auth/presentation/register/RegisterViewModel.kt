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
package com.flexcode.wedate.auth.presentation.register

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.flexcode.wedate.auth.data.local.datastore.AuthDataStore
import com.flexcode.wedate.auth.data.models.User
import com.flexcode.wedate.common.BaseViewModel
import com.flexcode.wedate.common.R.string as AppText
import com.flexcode.wedate.common.data.LogService
import com.flexcode.wedate.common.extestions.*
import com.flexcode.wedate.common.navigation.IDENTITY_SCREEN
import com.flexcode.wedate.common.navigation.LOGIN_SCREEN
import com.flexcode.wedate.common.navigation.REGISTER_SCREEN
import com.flexcode.wedate.common.snackbar.SnackBarManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class RegisterViewModel @Inject constructor(
    logService: LogService,
    private val dataStore: AuthDataStore
) : BaseViewModel(logService) {

    var uiState = mutableStateOf(RegisterUiState())
        private set

    val user = mutableStateOf(User())

    private val email
        get() = uiState.value.email
    private val password
        get() = uiState.value.password

    private val firstName
        get() = uiState.value.firstName

    private val phoneNumber
        get() = uiState.value.phoneNumber

    fun onEmailChange(newValue: String) {
        uiState.value = uiState.value.copy(email = newValue)
        user.value = user.value.copy(email = newValue)
    }

    fun onFirstNameChange(newValue: String) {
        uiState.value = uiState.value.copy(firstName = newValue)
        user.value = user.value.copy(firstName = newValue)
    }

    fun onPhoneNumberChange(newValue: String) {
        uiState.value = uiState.value.copy(phoneNumber = newValue)
        user.value = user.value.copy(phoneNumber = newValue)
    }

    fun onPasswordChange(newValue: String) {
        uiState.value = uiState.value.copy(password = newValue)
        user.value = user.value.copy(password = newValue)
    }

//    fun onConfirmPasswordChange(newValue: String) {
//        uiState.value = uiState.value.copy(confirmPassword = newValue)
//    }

    fun onLoginClick(openAndPopUp: (String, String) -> Unit) {
        launchCatching { openAndPopUp(LOGIN_SCREEN, REGISTER_SCREEN) }
    }

    fun onRegisterClick(openScreen: (String) -> Unit) {
        if (!firstName.isFirstNameValid()) {
            SnackBarManager.showMessage(AppText.first_name_error)
            return
        }

        if (!phoneNumber.isPhoneNumberValid()) {
            SnackBarManager.showMessage(AppText.phone_error)
            return
        }

        if (!email.isValidEmail()) {
            SnackBarManager.showMessage(AppText.email_error)
            return
        }

        if (!password.isValidPassword()) {
            SnackBarManager.showMessage(AppText.password_error)
            return
        }

//        if (!password.passwordMatches(uiState.value.confirmPassword)) {
//            SnackBarManager.showMessage(AppText.password_match_error)
//            return
//        }

        saveUserInfoToDataStore()
        launchCatching {
            openScreen(IDENTITY_SCREEN)
        }
    }

    private fun saveUserInfoToDataStore() {
        viewModelScope.launch { dataStore.saveUserName(user.value.firstName) }
        viewModelScope.launch { dataStore.saveUserPhone(user.value.phoneNumber) }
        viewModelScope.launch { dataStore.saveUserEmail(user.value.email) }
        viewModelScope.launch { dataStore.saveUserPwd(user.value.password) }
    }
}
