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
package com.flexcode.wedate.matches.di

import com.flexcode.wedate.matches.domain.repository.MatchesRepository
import com.flexcode.wedate.matches.domain.use_case.GetAllUserMatchesUseCase
import com.flexcode.wedate.matches.domain.use_case.GetChatProfilesUseCase
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
            getChatProfilesUseCase = GetChatProfilesUseCase(repository = repository)
        )
    }
}
