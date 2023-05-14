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
package com.flexcode.wedatecompose.network.data.repository.profile_edit

import com.flexcode.wedate.common.utils.Resource
import com.flexcode.wedatecompose.network.domain.repository.profile_edit.EditProfileRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await

class EditProfileRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth
) : EditProfileRepository {
    private val dbRef = FirebaseDatabase.getInstance().reference

    override suspend fun updateUserProfileInfo(userBio: String, nickName: String):
        Flow<Resource<Any>> {
        return flow {
            emit(Resource.Loading())
            try {
                val userId = auth.uid!!
                dbRef.child(USER_PATH).child(userId).child("userBio")
                    .setValue(userBio).await()
                dbRef.child(USER_PATH).child(userId).child("nickName")
                    .setValue(nickName).await()
                emit(Resource.Success(Any()))
            } catch (e: Exception) {
                println(e)
                emit(Resource.Error(message = e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    companion object {
        const val USER_PATH = "WeDateUsers"
    }
}
