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
package com.flexcode.wedatecompose.di

import com.flexcode.wedate.common.data.LogService
import com.flexcode.wedate.common.data.LogServiceImpl
import com.flexcode.wedatecompose.network.data.repository.auth.AuthRepositoryImpl
import com.flexcode.wedatecompose.network.data.repository.chat.SaveChatRepositoryImpl
import com.flexcode.wedatecompose.network.data.repository.home.HomeRepositoryImpl
import com.flexcode.wedatecompose.network.data.repository.matches.MatchesRepositoryImpl
import com.flexcode.wedatecompose.network.data.repository.profile_edit.EditProfileRepositoryImpl
import com.flexcode.wedatecompose.network.domain.repository.auth.AuthRepository
import com.flexcode.wedatecompose.network.domain.repository.chat.SaveChatRepository
import com.flexcode.wedatecompose.network.domain.repository.home.HomeRepository
import com.flexcode.wedatecompose.network.domain.repository.matches.MatchesRepository
import com.flexcode.wedatecompose.network.domain.repository.profile_edit.EditProfileRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun provideAuthRepository(impl: AuthRepositoryImpl): AuthRepository

    @Binds
    abstract fun provideLogService(impl: LogServiceImpl): LogService

    @Binds
    abstract fun providesHomeRepository(impl: HomeRepositoryImpl): HomeRepository

    @Binds
    abstract fun providesMatchesRepository(impl: MatchesRepositoryImpl): MatchesRepository

    @Binds
    abstract fun providesSaveChatsRepository(impl: SaveChatRepositoryImpl): SaveChatRepository

    @Binds
    abstract fun providesEditProfileRepository(impl: EditProfileRepositoryImpl):
        EditProfileRepository
}
