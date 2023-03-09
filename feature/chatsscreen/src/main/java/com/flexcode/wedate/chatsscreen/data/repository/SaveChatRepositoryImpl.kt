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
package com.flexcode.wedate.chatsscreen.data.repository

import com.flexcode.wedate.chatsscreen.data.model.Messsage
import com.flexcode.wedate.chatsscreen.domain.repository.SaveChatRepository
import com.flexcode.wedate.common.utils.Resource
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

    companion object {
        const val CHATS_PATH = "WeDateChats"
        const val MESSAGES = "Messages"
    }
}
