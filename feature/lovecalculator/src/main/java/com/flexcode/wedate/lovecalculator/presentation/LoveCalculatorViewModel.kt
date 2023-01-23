package com.flexcode.wedate.lovecalculator.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flexcode.wedate.common.utils.Resource
import com.flexcode.wedate.lovecalculator.domain.usecases.CalculatorUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoveCalculatorViewModel @Inject constructor(
    private val calculatorUseCases: CalculatorUseCases
) : ViewModel() {

    var state = mutableStateOf(LoveState())
        private set

    private fun getLovePercentage(sname:String,fname: String){
        viewModelScope.launch {
            calculatorUseCases.invoke(sname, fname).collect{result ->
                when(result){
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