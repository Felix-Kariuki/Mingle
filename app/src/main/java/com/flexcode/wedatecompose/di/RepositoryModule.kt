package com.flexcode.wedatecompose.di

import com.flexcode.wedate.common.data.LogService
import com.flexcode.wedate.common.data.LogServiceImpl
import com.flexcode.wedate.auth.data.repository.AuthRepositoryImpl
import com.flexcode.wedate.auth.domain.repository.AuthRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds abstract fun provideAuthRepository(impl: AuthRepositoryImpl): AuthRepository

    @Binds abstract fun provideLogService(impl: LogServiceImpl): LogService


}