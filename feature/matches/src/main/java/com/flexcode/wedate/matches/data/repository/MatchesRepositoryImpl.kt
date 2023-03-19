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
package com.flexcode.wedate.matches.data.repository

import com.flexcode.wedate.common.utils.Resource
import com.flexcode.wedate.matches.data.model.ChatProfile
import com.flexcode.wedate.matches.data.model.Matches
import com.flexcode.wedate.matches.domain.repository.MatchesRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await

class MatchesRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth
) : MatchesRepository {
    private val dbRef = FirebaseDatabase.getInstance().reference

    override suspend fun getAllUserMatches(): Flow<Resource<List<Matches>>> {
        return flow<Resource<List<Matches>>> {
            emit(Resource.Loading())
            try {
                val currentUserId = auth.uid!!
                val allUserMatches = ArrayList<Matches>()
                val allMatches = dbRef.child(MATCHES).child(currentUserId).get().await()

                for (i in allMatches.children) {
                    val result = i.getValue(Matches::class.java)
                    allUserMatches.add(result!!)
                }
                emit(Resource.Success(allUserMatches))
            } catch (e: Exception) {
                emit(Resource.Error(message = e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getAllChatProfiles(): Flow<Resource<List<ChatProfile>>> {
        return flow<Resource<List<ChatProfile>>> {
            emit(Resource.Loading())
            try {
                val currentUserId = auth.uid!!
                val allUserMatches = ArrayList<ChatProfile>()
                val allMatches = dbRef.child(CHATS_PATH_PROFILE).child(currentUserId).get().await()

                for (i in allMatches.children) {
                    val result = i.getValue(ChatProfile::class.java)
                    allUserMatches.add(result!!)
                }
                emit(Resource.Success(allUserMatches))
            } catch (e: Exception) {
                emit(Resource.Error(message = e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    companion object {
        const val MATCHES = "Matches"
        const val CHATS_PATH_PROFILE = "WeDateChatsProfiles"
    }
}
