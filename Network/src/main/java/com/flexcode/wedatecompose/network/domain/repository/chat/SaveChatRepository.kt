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
package com.flexcode.wedatecompose.network.domain.repository.chat

import com.flexcode.wedate.common.utils.Resource
import com.flexcode.wedatecompose.network.data.models.chat.Messsage
import kotlinx.coroutines.flow.Flow

interface SaveChatRepository {

    suspend fun saveChat(
        matchId: String,
        lastMsg: String,
        lastMsgTime: Long,
        message: String,
        messageTime: Long,
        messageSenderId: String
    ): Flow<Resource<Any>>

    suspend fun saveChatToMatch(
        matchId: String,
        lastMsg: String,
        lastMsgTime: Long,
        message: String,
        messageTime: Long,
        messageSenderId: String
    ): Flow<Resource<Any>>

    suspend fun getAllMessages(messagesId: String): Flow<Resource<List<Messsage>>>

    suspend fun saveChatProfileToCurrentUser(
        crushUserId: String,
        firstName: String,
        profileImage: String,
        lastMsgTime: Long,
        lastMsg: String
    ): Flow<Resource<Any>>

    suspend fun saveChatProfileToCrush(
        crushUserId: String,
        firstName: String,
        profileImage: String,
        lastMsgTime: Long,
        lastMsg: String
    ): Flow<Resource<Any>>
}
