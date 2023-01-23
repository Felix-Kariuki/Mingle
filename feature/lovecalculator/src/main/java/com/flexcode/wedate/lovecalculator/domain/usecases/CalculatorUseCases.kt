package com.flexcode.wedate.lovecalculator.domain.usecases

import com.flexcode.wedate.common.utils.Resource
import com.flexcode.wedate.lovecalculator.domain.model.CalculatorResponse
import com.flexcode.wedate.lovecalculator.domain.repository.CalculatorRepository
import kotlinx.coroutines.flow.Flow

class CalculatorUseCases(
    private val repository: CalculatorRepository
) {
    suspend operator fun invoke(sname:String, fname:String):Flow<Resource<CalculatorResponse>> {
        return repository.getLovePercentage(sname, fname)
    }
}