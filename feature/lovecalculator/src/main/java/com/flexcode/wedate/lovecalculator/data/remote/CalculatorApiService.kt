package com.flexcode.wedate.lovecalculator.data.remote

import com.flexcode.wedate.lovecalculator.data.dto.CalculatorResponseDto
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query

interface CalculatorApiService {

    @Headers("X-RapidAPI-Key: 5c6b1a3c50mshc531bd00715a1ccp1ef009jsn3a061edd38c3")
    @GET("getPercentage")
    suspend fun getLovePercentage(
        @Query("sname")sname:String,
        @Query("fname")fname:String
    ):CalculatorResponseDto
}