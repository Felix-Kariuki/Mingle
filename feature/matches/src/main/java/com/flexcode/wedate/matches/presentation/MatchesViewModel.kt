package com.flexcode.wedate.matches.presentation

import androidx.compose.runtime.mutableStateOf
import com.flexcode.wedate.common.BaseViewModel
import com.flexcode.wedate.common.data.LogService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MatchesViewModel @Inject constructor(
    logService: LogService
) : BaseViewModel(logService) {
    var state = mutableStateOf(MatchesState())
        private set

    private val searchValue
        get() = state.value.searchValue

    fun onSearchValueChange(value:String){
        state.value = state.value.copy(searchValue = value)
    }

}