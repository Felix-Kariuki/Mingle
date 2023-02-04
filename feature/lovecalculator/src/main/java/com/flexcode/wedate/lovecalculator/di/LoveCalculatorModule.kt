package com.flexcode.wedate.lovecalculator.di

import com.flexcode.wedate.lovecalculator.data.remote.CalculatorApiService
import com.flexcode.wedate.lovecalculator.domain.repository.CalculatorRepository
import com.flexcode.wedate.lovecalculator.domain.usecases.CalculatorUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LoveCalculatorModule {
    private const val REQUEST_TIME_OUT = 60L

    @Provides
    @Singleton
    fun providesLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    @Provides
    @Singleton
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(REQUEST_TIME_OUT, TimeUnit.SECONDS)
            .build()

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): CalculatorApiService =
        retrofit.create()


    const val BASE_URL = "https://love-calculator.p.rapidapi.com/"


    @Singleton
    @Provides
    fun providesCalculatorUseCase(
        calculatorRepository: CalculatorRepository
    ): CalculatorUseCases{
        return CalculatorUseCases(calculatorRepository)
    }

}
