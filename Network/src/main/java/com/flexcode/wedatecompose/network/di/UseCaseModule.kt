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
package com.flexcode.wedatecompose.network.di

import com.flexcode.wedatecompose.network.domain.repository.auth.AuthRepository
import com.flexcode.wedatecompose.network.domain.repository.auth.ProfileImageRepository
import com.flexcode.wedatecompose.network.domain.repository.chat.SaveChatRepository
import com.flexcode.wedatecompose.network.domain.repository.home.HomeRepository
import com.flexcode.wedatecompose.network.domain.repository.matches.MatchesRepository
import com.flexcode.wedatecompose.network.domain.repository.profile_edit.EditProfileRepository
import com.flexcode.wedatecompose.network.domain.use_cases.auth.DeleteAccountUseCase
import com.flexcode.wedatecompose.network.domain.use_cases.auth.GetUserDetailsUseCase
import com.flexcode.wedatecompose.network.domain.use_cases.auth.LoginUseCase
import com.flexcode.wedatecompose.network.domain.use_cases.auth.ProfileImageUseCase
import com.flexcode.wedatecompose.network.domain.use_cases.auth.RegisterUseCase
import com.flexcode.wedatecompose.network.domain.use_cases.auth.UpdateProfileImageUseCase
import com.flexcode.wedatecompose.network.domain.use_cases.auth.UseCaseContainer
import com.flexcode.wedatecompose.network.domain.use_cases.chat.ChatsUseCaseContainer
import com.flexcode.wedatecompose.network.domain.use_cases.chat.GetMessagesUseCase
import com.flexcode.wedatecompose.network.domain.use_cases.chat.SaveChatProfileToCrushUseCase
import com.flexcode.wedatecompose.network.domain.use_cases.chat.SaveChatProfileToCurrentUserUseCase
import com.flexcode.wedatecompose.network.domain.use_cases.chat.SaveChatToCurrentUserUseCase
import com.flexcode.wedatecompose.network.domain.use_cases.chat.SaveChatToMatchUseCase
import com.flexcode.wedatecompose.network.domain.use_cases.home.DeleteLikedByFromUseCase
import com.flexcode.wedatecompose.network.domain.use_cases.home.GetAllLikedByUseCase
import com.flexcode.wedatecompose.network.domain.use_cases.home.GetAllUsersUseCase
import com.flexcode.wedatecompose.network.domain.use_cases.home.HomeUseCases
import com.flexcode.wedatecompose.network.domain.use_cases.home.SaveLikeUseCase
import com.flexcode.wedatecompose.network.domain.use_cases.home.SaveMatchToCurrentUserUseCase
import com.flexcode.wedatecompose.network.domain.use_cases.home.SaveMatchUseCase
import com.flexcode.wedatecompose.network.domain.use_cases.home.UpdateUserAgeUseCase
import com.flexcode.wedatecompose.network.domain.use_cases.home.UpdateUserProfileInfoUseCase
import com.flexcode.wedatecompose.network.domain.use_cases.matches.GetAllUserMatchesUseCase
import com.flexcode.wedatecompose.network.domain.use_cases.matches.GetChatProfilesUseCase
import com.flexcode.wedatecompose.network.domain.use_cases.matches.MatchesUseCaseContainer
import com.flexcode.wedatecompose.network.domain.use_cases.profile_edit.EditProfileUseCase
import com.flexcode.wedatecompose.network.domain.use_cases.profile_edit.EditUseCaseContainer
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
    fun providesAuthUseCaseContainer(
        authRepository: AuthRepository,
        repository: ProfileImageRepository
    ): UseCaseContainer {
        return UseCaseContainer(
            loginUseCase = LoginUseCase(repository = authRepository),
            registerUseCase = RegisterUseCase(repository = authRepository),
            getUserDetailsUseCase = GetUserDetailsUseCase(repository = authRepository),
            profileImageUseCase = ProfileImageUseCase(repository = repository),
            updateProfileImageUseCase = UpdateProfileImageUseCase(repository = repository),
            deleteAccountUseCase = DeleteAccountUseCase(repository = authRepository)
        )
    }

    @Provides
    @Singleton
    fun providesHomeUseCaseContainer(
        repository: HomeRepository
    ): HomeUseCases {
        return HomeUseCases(
            updateUserProfileInfoUseCase = UpdateUserProfileInfoUseCase(repository = repository),
            getAllUsersUseCase = GetAllUsersUseCase(repository = repository),
            saveLikeUseCase = SaveLikeUseCase(repository = repository),
            getAllLikedByUseCase = GetAllLikedByUseCase(repository = repository),
            saveMatchUseCase = SaveMatchUseCase(repository = repository),
            saveMatchToCurrentUserUseCase = SaveMatchToCurrentUserUseCase(repository = repository),
            updateUserAgeUseCase = UpdateUserAgeUseCase(repository = repository),
            deleteLikedByFromUseCase = DeleteLikedByFromUseCase(homeRepository = repository)
        )
    }

    @Provides
    @Singleton
    fun providesEditProfileUseCaseContainer(
        repository: EditProfileRepository
    ): EditUseCaseContainer {
        return EditUseCaseContainer(
            editProfileUseCase = EditProfileUseCase(repository = repository)
        )
    }

    @Provides
    @Singleton
    fun providesMatchesUseCaseContainer(
        repository: MatchesRepository
    ): MatchesUseCaseContainer {
        return MatchesUseCaseContainer(
            getAllUserMatchesUseCase = GetAllUserMatchesUseCase(repository = repository),
            getChatProfilesUseCase = GetChatProfilesUseCase(repository = repository)
        )
    }

    @Provides
    @Singleton
    fun providesChatsUseCaseContainer(
        repository: SaveChatRepository
    ): ChatsUseCaseContainer {
        return ChatsUseCaseContainer(
            saveChatToCurrentUserUseCase = SaveChatToCurrentUserUseCase(repository = repository),
            saveChatToMatchUseCase = SaveChatToMatchUseCase(repository = repository),
            getMessagesUseCase = GetMessagesUseCase(repository = repository),
            saveChatProfileToCrushUseCase = SaveChatProfileToCrushUseCase(repository),
            saveChatProfileToCurrentUserUseCase = SaveChatProfileToCurrentUserUseCase(repository)
        )
    }
}
