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
package com.flexcode.wedate.profileedit.di

import com.flexcode.wedate.profileedit.domain.repository.EditProfileRepository
import com.flexcode.wedate.profileedit.domain.use_case.EditProfileUseCase
import com.flexcode.wedate.profileedit.domain.use_case.EditUseCaseContainer
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
        repository: EditProfileRepository
    ): EditUseCaseContainer {
        return EditUseCaseContainer(
            editProfileUseCase = EditProfileUseCase(repository = repository)
        )
    }
}
