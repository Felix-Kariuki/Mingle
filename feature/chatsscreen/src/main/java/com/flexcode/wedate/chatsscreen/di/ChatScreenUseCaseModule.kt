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
package com.flexcode.wedate.chatsscreen.di

import com.flexcode.wedate.chatsscreen.domain.repository.SaveChatRepository
import com.flexcode.wedate.chatsscreen.domain.use_cases.ChatsUseCaseContainer
import com.flexcode.wedate.chatsscreen.domain.use_cases.GetMessagesUseCase
import com.flexcode.wedate.chatsscreen.domain.use_cases.SaveChatToCurrentUserUseCase
import com.flexcode.wedate.chatsscreen.domain.use_cases.SaveChatToMatchUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ChatScreenUseCaseModule {

    @Provides
    @Singleton
    fun providesChatsUseCaseContainer(
        repository: SaveChatRepository
    ): ChatsUseCaseContainer {
        return ChatsUseCaseContainer(
            saveChatToCurrentUserUseCase = SaveChatToCurrentUserUseCase(repository = repository),
            saveChatToMatchUseCase = SaveChatToMatchUseCase(repository = repository),
            getMessagesUseCase = GetMessagesUseCase(repository = repository)
        )
    }
}
