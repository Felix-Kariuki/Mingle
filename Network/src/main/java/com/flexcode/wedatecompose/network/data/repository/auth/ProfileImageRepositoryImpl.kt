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
package com.flexcode.wedatecompose.network.data.repository.auth

import android.net.Uri
import com.flexcode.wedate.common.utils.Resource
import com.flexcode.wedatecompose.network.domain.repository.auth.ProfileImageRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class ProfileImageRepositoryImpl @Inject constructor(
    private val storage: FirebaseStorage,
    private val auth: FirebaseAuth
) : ProfileImageRepository {

    private val dbRef = FirebaseDatabase.getInstance().reference
    override suspend fun uploadImageToFirebaseStorage(
        imageUri: Uri,
        imageNumber: String
    ): Flow<Resource<Any>> {
        return flow {
            emit(Resource.Loading())
            try {
                val downloadUrl = storage.reference.child(IMAGES).child(PROFILE_IMAGE).child(
                    auth.currentUser?.uid.toString()
                )
                    .child("$imageNumber.jpg")
                    .putFile(imageUri).await()
                    .storage.downloadUrl.await()
                emit(Resource.Success(downloadUrl))
            } catch (e: Exception) {
                emit(Resource.Error(e.message.toString()))
            }
        }
    }

    override suspend fun uploadImageUrlToFirebaseDatabase(
        imageUrl: String,
        imageNumber: String
    ): Flow<Resource<Boolean>> {
        return flow {
            emit(Resource.Loading())
            try {
                val uid = auth.uid!!
                dbRef.child(USER_PATH).child(uid).child(IMAGE_PATH).child(imageNumber)
                    .setValue(imageUrl).await()
                emit(Resource.Success(true))
            } catch (e: Exception) {
                emit(Resource.Error(e.message.toString()))
            }
        }
    }

    companion object {
        const val IMAGES = "WeDateImages"
        const val USER_PATH = "WeDateUsers"
        const val IMAGE_PATH = "profileImage"
        const val PROFILE_IMAGE = "profileImages"
    }
}
