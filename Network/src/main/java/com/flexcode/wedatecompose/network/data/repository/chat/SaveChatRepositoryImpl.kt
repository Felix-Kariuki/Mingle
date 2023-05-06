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
package com.flexcode.wedatecompose.network.data.repository.chat

import com.flexcode.wedate.common.utils.Resource
import com.flexcode.wedatecompose.network.data.models.chat.Messsage
import com.flexcode.wedatecompose.network.data.models.matches.ChatProfile
import com.flexcode.wedatecompose.network.domain.repository.chat.SaveChatRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.util.UUID
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await

class SaveChatRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth
) : SaveChatRepository {
    private val dbRef = FirebaseDatabase.getInstance().reference

    override suspend fun getAllMessages(messagesId: String): Flow<Resource<List<Messsage>>> {
        return flow<Resource<List<Messsage>>> {
            emit(Resource.Loading())
            val allMessages = ArrayList<Messsage>()
            val msgs = dbRef.child(CHATS_PATH).child(messagesId).child(MESSAGES).get().await()
            for (i in msgs.children) {
                val result = i.getValue(Messsage::class.java)
                allMessages.add(result!!)
            }
            try {
                emit(Resource.Success(allMessages))
            } catch (e: Exception) {
                emit(Resource.Error(message = e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun saveChat(
        matchId: String,
        lastMsg: String,
        lastMsgTime: Long,
        message: String,
        messageTime: Long,
        messageSenderId: String
    ): Flow<Resource<Any>> {
        return flow<Resource<Any>> {
            emit(Resource.Loading())

            try {
                val messageId = UUID.randomUUID().toString()
                val currentUid = auth.uid!!
                val currentUserChatId = currentUid + matchId

                val messsage = Messsage(
                    message = message,
                    timeStamp = messageTime,
                    messageSenderId = messageSenderId
                )

                dbRef.child(CHATS_PATH).child(currentUserChatId).child(MESSAGES).child(messageId)
                    .setValue(messsage).await()
                /*dbRef.child(CHATS_PATH).child(currentUserChatId).child("lastMsg")
                    .setValue(lastMsg).await()
                dbRef.child(CHATS_PATH).child(currentUserChatId).child("lastMsgTime")
                    .setValue(lastMsgTime).await()*/
            } catch (e: Exception) {
                println(e)
                emit(Resource.Error(message = e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun saveChatToMatch(
        matchId: String,
        lastMsg: String,
        lastMsgTime: Long,
        message: String,
        messageTime: Long,
        messageSenderId: String
    ): Flow<Resource<Any>> {
        return flow<Resource<Any>> {
            emit(Resource.Loading())

            try {
                val currentUid = auth.uid!!
                val matchIdChatId = matchId + currentUid
                val messageId = UUID.randomUUID().toString()
                val messsage = Messsage(
                    message = message,
                    timeStamp = messageTime,
                    messageSenderId = messageSenderId
                )

                dbRef.child(CHATS_PATH).child(matchIdChatId).child(MESSAGES).child(messageId)
                    .setValue(messsage).await()
                /*dbRef.child(CHATS_PATH).child(matchIdChatId).child("lastMsg")
                    .setValue(lastMsg).await()
                dbRef.child(CHATS_PATH).child(matchIdChatId).child("lastMsgTime")
                    .setValue(lastMsgTime).await()*/
            } catch (e: Exception) {
                println(e)
                emit(Resource.Error(message = e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun saveChatProfileToCurrentUser(
        crushUserId: String,
        firstName: String,
        profileImage: String,
        lastMsgTime: Long,
        lastMsg: String
    ): Flow<Resource<Any>> {
        return flow {
            emit(Resource.Loading())

            try {
                val currentUid = auth.uid!!
                val chatProfile = ChatProfile(
                    id = crushUserId,
                    firstName = firstName,
                    profileImage = profileImage,
                    lastMsgTime = System.currentTimeMillis(),
                    lastMsg = lastMsg
                )

                dbRef.child(CHATS_PATH_PROFILE).child(currentUid).child(crushUserId)
                    .setValue(chatProfile).await()
                emit(Resource.Success(Any()))
            } catch (e: Exception) {
                println(e)
                emit(Resource.Error(message = e.message.toString()))
            }
        }
    }

    override suspend fun saveChatProfileToCrush(
        crushUserId: String,
        firstName: String,
        profileImage: String,
        lastMsgTime: Long,
        lastMsg: String
    ): Flow<Resource<Any>> {
        return flow {
            emit(Resource.Loading())

            try {
                val currentUid = auth.uid!!
                val chatProfile = ChatProfile(
                    id = currentUid,
                    firstName = firstName,
                    profileImage = profileImage,
                    lastMsgTime = System.currentTimeMillis(),
                    lastMsg = lastMsg
                )
                dbRef.child(CHATS_PATH_PROFILE).child(crushUserId).child(currentUid)
                    .setValue(chatProfile).await()
                emit(Resource.Success(Any()))
            } catch (e: Exception) {
                println(e)
                emit(Resource.Error(message = e.message.toString()))
            }
        }
    }

    companion object {
        const val CHATS_PATH = "WeDateChats"
        const val CHATS_PATH_PROFILE = "WeDateChatsProfiles"
        const val MESSAGES = "Messages"
    }
}
