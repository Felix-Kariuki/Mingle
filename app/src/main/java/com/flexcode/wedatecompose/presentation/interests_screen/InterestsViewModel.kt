package com.flexcode.wedatecompose.presentation.interests_screen

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.datastore.dataStore
import androidx.lifecycle.viewModelScope
import com.flexcode.wedatecompose.common.BaseViewModel
import com.flexcode.wedatecompose.common.navigation.SEARCHING_FOR_SCREEN
import com.flexcode.wedatecompose.data.LogService
import com.flexcode.wedatecompose.data.local.datastore.AuthDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
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