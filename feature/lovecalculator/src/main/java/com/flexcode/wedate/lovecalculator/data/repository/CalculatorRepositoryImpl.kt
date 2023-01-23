package com.flexcode.wedate.lovecalculator.data.repository

import com.flexcode.wedate.common.utils.Resource
import com.flexcode.wedate.lovecalculator.data.mapper.toDomain
import com.flexcode.wedate.lovecalculator.data.remote.CalculatorApiService
import com.flexcode.wedate.lovecalculator.domain.model.CalculatorResponse
import com.flexcode.wedate.lovecalculator.domain.repository.CalculatorRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class CalculatorRepositoryImpl @Inject constructor(
    private val calculatorService: CalculatorApiService
): CalculatorRepository {
    override suspend fun getLovePercentage(
        sname: String,
        fname: String
    ): Flow<Resource<CalculatorResponse>> = flow {
        emit(Resource.Loading())

        try {
            val response = calculatorService.getLovePercentage(sname, fname).toDomain()
            emit(Resource.Success(data = response))
        }catch (e:IOException){
            emit(Resource.Error(
                message = "Error!! ${e.message}"
            ))
        }catch (e:HttpException){
            emit(Resource.Error(
                message = "Error ${e.message}"
            ))
        }
    }

}