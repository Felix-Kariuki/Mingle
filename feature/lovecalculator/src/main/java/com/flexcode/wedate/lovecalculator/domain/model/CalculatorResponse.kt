package com.flexcode.wedate.lovecalculator.domain.model

@kotlinx.serialization.Serializable
data class CalculatorResponse(
    val fname: String,
    val percentage: String,
    val result: String,
    val sname: String
)