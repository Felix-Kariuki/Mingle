package com.flexcode.wedate.lovecalculator.data.mapper

import com.flexcode.wedate.lovecalculator.data.dto.CalculatorResponseDto
import com.flexcode.wedate.lovecalculator.domain.model.CalculatorResponse

internal fun CalculatorResponseDto.toDomain(): CalculatorResponse{
    return CalculatorResponse(
        fname = fname,
        percentage = percentage,
        result = result,
        sname = sname
    )
}