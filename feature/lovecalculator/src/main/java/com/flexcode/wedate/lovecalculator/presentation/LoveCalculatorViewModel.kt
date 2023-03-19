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
package com.flexcode.wedate.lovecalculator.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.flexcode.wedate.common.BaseViewModel
import com.flexcode.wedate.common.R.string as AppText
import com.flexcode.wedate.common.data.LogService
import com.flexcode.wedate.common.ext.isNameValid
import com.flexcode.wedate.common.snackbar.SnackBarManager
import com.flexcode.wedate.common.utils.Resource
import com.flexcode.wedate.lovecalculator.domain.usecases.CalculatorUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

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
