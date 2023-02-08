package com.flexcode.wedate.home.di


import com.flexcode.wedate.home.domain.repository.HomeRepository
import com.flexcode.wedate.home.domain.use_cases.*
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
            updateUserProfileInfoUseCase = UpdateUserProfileInfoUseCase(repository=repository),
            getAllUsersUseCase = GetAllUsersUseCase(repository=repository),
            saveLikeUseCase = SaveLikeUseCase(repository=repository),
            getAllLikedByUseCase = GetAllLikedByUseCase(repository=repository),
            saveMatchUseCase = SaveMatchUseCase(repository=repository),
            saveMatchToCurrentUserUseCase = SaveMatchToCurrentUserUseCase(repository=repository),
        )
    }

}
