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
package com.flexcode.wedate.lovecalculator.data.repository

import com.flexcode.wedate.common.utils.Resource
import com.flexcode.wedate.lovecalculator.data.mapper.toDomain
import com.flexcode.wedate.lovecalculator.data.remote.CalculatorApiService
import com.flexcode.wedate.lovecalculator.domain.model.CalculatorResponse
import com.flexcode.wedate.lovecalculator.domain.repository.CalculatorRepository
import java.io.IOException
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

class CalculatorRepositoryImpl @Inject constructor(
    private val calculatorService: CalculatorApiService
) : CalculatorRepository {
    override suspend fun getLovePercentage(
        sname: String,
        fname: String
    ): Flow<Resource<CalculatorResponse>> = flow {
        emit(Resource.Loading())

        try {
            val response = calculatorService.getLovePercentage(sname, fname).toDomain()
            emit(Resource.Success(data = response))
        } catch (e: IOException) {
            emit(
                Resource.Error(
                    message = "Error!! ${e.message}"
                )
            )
        } catch (e: HttpException) {
            emit(
                Resource.Error(
                    message = "Error ${e.message}"
                )
            )
        }
    }
}
