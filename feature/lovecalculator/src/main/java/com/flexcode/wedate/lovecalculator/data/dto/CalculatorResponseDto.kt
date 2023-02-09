package com.flexcode.wedate.lovecalculator.data.dto

@kotlinx.serialization.Serializable
data class CalculatorResponseDto(
    val fname: String,
    val percentage: String,
    val result: String,
    val sname: String
)