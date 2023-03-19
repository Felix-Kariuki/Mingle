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

import com.flexcode.wedate.auth.data.repository.AuthRepositoryImpl
import com.flexcode.wedate.auth.domain.repository.AuthRepository
import com.flexcode.wedate.chatsscreen.data.repository.SaveChatRepositoryImpl
import com.flexcode.wedate.chatsscreen.domain.repository.SaveChatRepository
import com.flexcode.wedate.common.data.LogService
import com.flexcode.wedate.common.data.LogServiceImpl
import com.flexcode.wedate.home.data.repository.HomeRepositoryImpl
import com.flexcode.wedate.home.domain.repository.HomeRepository
import com.flexcode.wedate.matches.data.repository.MatchesRepositoryImpl
import com.flexcode.wedate.matches.domain.repository.MatchesRepository
import com.flexcode.wedate.profileedit.data.repository.EditProfileRepositoryImpl
import com.flexcode.wedate.profileedit.domain.repository.EditProfileRepository
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
