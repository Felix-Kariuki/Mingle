package com.flexcode.wedate.lovecalculator.presentation

import com.flexcode.wedate.lovecalculator.domain.model.CalculatorResponse

data class LoveState(
    val calculatorResponse: CalculatorResponse? = null,
    val isLoading:Boolean = false
)
