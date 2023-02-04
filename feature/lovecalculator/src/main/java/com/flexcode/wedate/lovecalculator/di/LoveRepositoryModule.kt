package com.flexcode.wedate.lovecalculator.di

import com.flexcode.wedate.lovecalculator.data.repository.CalculatorRepositoryImpl
import com.flexcode.wedate.lovecalculator.domain.repository.CalculatorRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class LoveRepositoryModule {

    @Binds
    abstract fun providesLoveCalculatorRepository(
        impl: CalculatorRepositoryImpl
    ): CalculatorRepository
}