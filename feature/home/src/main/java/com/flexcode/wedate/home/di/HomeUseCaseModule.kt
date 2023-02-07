package com.flexcode.wedate.home.di


import com.flexcode.wedate.home.domain.repository.HomeRepository
import com.flexcode.wedate.home.domain.use_cases.GetAllUsersUseCase
import com.flexcode.wedate.home.domain.use_cases.HomeUseCases
import com.flexcode.wedate.home.domain.use_cases.SaveLikeUseCase
import com.flexcode.wedate.home.domain.use_cases.UpdateUserProfileInfoUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HomeUseCaseModule {

    @Provides
    @Singleton
    fun providesHomeUseCaseContainer(
        repository: HomeRepository
    ): HomeUseCases {
        return HomeUseCases(
            updateUserProfileInfoUseCase = UpdateUserProfileInfoUseCase(repository),
            getAllUsersUseCase = GetAllUsersUseCase(repository),
            saveLikeUseCase = SaveLikeUseCase(repository),
        )
    }

}
