package com.flexcode.wedate.matches.di

import com.flexcode.wedate.matches.domain.repository.MatchesRepository
import com.flexcode.wedate.matches.domain.use_case.GetAllUserMatchesUseCase
import com.flexcode.wedate.matches.domain.use_case.UseCaseContainer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MatchesUseCaseModule {

    @Provides
    @Singleton
    fun providesHomeUseCaseContainer(
        repository: MatchesRepository
    ): UseCaseContainer {
        return UseCaseContainer(
            getAllUserMatchesUseCase = GetAllUserMatchesUseCase(repository = repository),
        )
    }

}