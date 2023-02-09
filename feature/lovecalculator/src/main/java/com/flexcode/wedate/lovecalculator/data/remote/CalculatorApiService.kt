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
package com.flexcode.wedate.lovecalculator.data.remote

import com.flexcode.wedate.lovecalculator.data.dto.CalculatorResponseDto
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface CalculatorApiService {

    @Headers("X-RapidAPI-Key: 5c6b1a3c50mshc531bd00715a1ccp1ef009jsn3a061edd38c3")
    @GET("getPercentage")
    suspend fun getLovePercentage(
        @Query("sname") sname: String,
        @Query("fname") fname: String
    ): CalculatorResponseDto
}
