package com.flexcode.wedate.lovecalculator.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.flexcode.wedate.common.BaseViewModel
import com.flexcode.wedate.common.data.LogService
import com.flexcode.wedate.common.ext.isNameValid
import com.flexcode.wedate.common.snackbar.SnackBarManager
import com.flexcode.wedate.common.utils.Resource
import com.flexcode.wedate.lovecalculator.domain.usecases.CalculatorUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import com.flexcode.wedate.common.R.string as AppText

@HiltViewModel
class LoveCalculatorViewModel @Inject constructor(
    private val calculatorUseCases: CalculatorUseCases,
    logService: LogService
) : BaseViewModel(logService) {

    var state = mutableStateOf(LoveState())
        private set

    private val yourName
        get() = state.value.yourName

    private val crushName
        get() = state.value.crushName

    fun onYourNameChange(value: String) {
        state.value = state.value.copy(yourName = value)
    }

    fun onCrushNameChange(value: String) {
        state.value = state.value.copy(crushName = value)
    }

    fun getLovePercentage() {
        if (!yourName.isNameValid() || !crushName.isNameValid()) {
            SnackBarManager.showMessage(AppText.name_error)
            return
        }
        viewModelScope.launch {
            calculatorUseCases.invoke(crushName, yourName).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        state.value = LoveState(calculatorResponse = result.data)
                        Timber.d("RESULTSS:: ${result.data}")
                    }
                    is Resource.Loading -> {
                        state.value = LoveState(isLoading = true)
                    }
                    is Resource.Error -> {
                        state.value = LoveState(calculatorResponse = result.data)
                    }

                }
            }
        }
    }
}


