package com.flexcode.wedate.auth.presentation.interests_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.flexcode.wedate.common.BaseViewModel
import com.flexcode.wedate.common.navigation.SEARCHING_FOR_SCREEN
import com.flexcode.wedate.auth.data.local.datastore.AuthDataStore
import com.flexcode.wedate.common.data.LogService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InterestsViewModel @Inject constructor(
    logService: LogService,
    private val dataStore: AuthDataStore
): BaseViewModel(logService) {

    fun onContinueClicked(openScreen:(String)->Unit){
        launchCatching { openScreen(SEARCHING_FOR_SCREEN) }
    }

    private val _selectedInterestsOption = mutableStateOf("Women")
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