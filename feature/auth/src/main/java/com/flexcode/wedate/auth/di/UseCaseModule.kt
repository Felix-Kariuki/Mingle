package com.flexcode.wedate.auth.di

import com.flexcode.wedate.auth.domain.repository.AuthRepository
import com.flexcode.wedate.auth.domain.usecase.GetUserDetailsUseCase
import com.flexcode.wedate.auth.domain.usecase.LoginUseCase
import com.flexcode.wedate.auth.domain.usecase.RegisterUseCase
import com.flexcode.wedate.auth.domain.usecase.UseCaseContainer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun providesUseCaseContainer(
        authRepository: AuthRepository
    ): UseCaseContainer {
        return UseCaseContainer(
            loginUseCase = LoginUseCase(repository = authRepository),
            registerUseCase = RegisterUseCase(repository = authRepository),
            getUserDetailsUseCase = GetUserDetailsUseCase(repository = authRepository)
        )
    }
}