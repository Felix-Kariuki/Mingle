package com.flexcode.wedatecompose.presentation.identity_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.flexcode.wedatecompose.common.BaseViewModel
import com.flexcode.wedatecompose.common.navigation.INTERESTS_SCREEN
import com.flexcode.wedatecompose.common.snackbar.SnackBarManager
import com.flexcode.wedatecompose.data.LogService
import com.flexcode.wedatecompose.data.local.datastore.AuthDataStore
import com.flexcode.wedatecompose.data.models.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.flexcode.wedatecompose.R.string as AppText

@HiltViewModel
class IdentifyScreenViewModel @Inject constructor(
    logService: LogService,
    private val dataStore: AuthDataStore
) : BaseViewModel(logService) {

    val user = mutableStateOf(User())
    var uiState = mutableStateOf(IdentityUiState())
        private set

    private val dd
     get() = uiState.value.dd

    private val mm
        get() = uiState.value.mm

    private val yy
        get() = uiState.value.yy

    private val _selectedGenderOption = mutableStateOf("Male")
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

        //save year of birth
        val yob =
            user.value.dateOfBirth + "/" + user.value.monthOfBirth + "/" + user.value.yearOfBirth

        launchCatching {
            dataStore.saveYearOfBirth(year = yob)
            openScreen(INTERESTS_SCREEN)
        }
    }

    fun onDateOfBirthChange(newValue: String) {
        user.value = user.value.copy(dateOfBirth = newValue)
    }

    fun onYearOfBirthChange(newValue: String) {
        user.value = user.value.copy(yearOfBirth = newValue)
    }

    fun onMonthOfBirthChange(newValue: String) {
        user.value = user.value.copy(monthOfBirth = newValue)
    }

    /*fun saveYearOfBirth() {
        val yob =
            user.value.dateOfBirth + "/" + user.value.monthOfBirth + "/" + user.value.yearOfBirth
        if (!dd.isDateOfBirthValid()){
            SnackBarManager.showMessage(AppText.dd_error)
            return
        }

        launchCatching {
            dataStore.saveYearOfBirth(year = yob)
        }
    }*/
}