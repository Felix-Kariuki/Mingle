package com.flexcode.wedate.lovecalculator.domain.repository

import com.flexcode.wedate.common.utils.Resource
import com.flexcode.wedate.lovecalculator.domain.model.CalculatorResponse
import kotlinx.coroutines.flow.Flow

interface CalculatorRepository {

    suspend fun getLovePercentage(sname:String,fname:String): Flow<Resource<CalculatorResponse>>
}