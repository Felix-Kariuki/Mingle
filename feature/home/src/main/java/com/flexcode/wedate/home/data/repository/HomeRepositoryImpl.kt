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
package com.flexcode.wedate.home.data.repository

import com.flexcode.wedate.auth.data.models.User
import com.flexcode.wedate.common.utils.Resource
import com.flexcode.wedate.home.data.model.Likes
import com.flexcode.wedate.home.domain.repository.HomeRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await

class HomeRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth
) : HomeRepository {
    private val dbRef = FirebaseDatabase.getInstance().reference

    override suspend fun updateUserProfileInfo(
        longitude: String,
        latitude: String,
        locationName: String
    ): Flow<Resource<Any>> {
        return flow {
            emit(Resource.Loading())
            try {
                val uid = auth.uid!!
                dbRef.child(USER_PATH).child(uid).child("locationName")
                    .setValue(locationName).await()
                dbRef.child(USER_PATH).child(uid).child("longitude")
                    .setValue(longitude).await()
                dbRef.child(USER_PATH).child(uid).child("latitude")
                    .setValue(latitude).await()
                emit(Resource.Success(Any()))
            } catch (e: Exception) {
                println(e)
                emit(Resource.Error(message = e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun saveLike(
        crushUserId: String,
        firstName: String,
        locationName: String,
        years: String,
        lat: String,
        long: String,
        profileImage: String,
        matched: Boolean
    ): Flow<Resource<Any>> {
        return flow {
            emit(Resource.Loading())
            try {
                val likeId = UUID.randomUUID().toString()
                val currentUid = auth.uid!!
                val likes = Likes(
                    id = currentUid,
                    date = System.currentTimeMillis(),
                    firstName = firstName,
                    locationName = locationName,
                    years = years,
                    lat = lat,
                    long = long,
                    profileImage = profileImage,
                    matched = matched
                )
                dbRef.child(LIKES).child(crushUserId).child(currentUid).setValue(likes).await()
                dbRef.child(USER_PATH).child(crushUserId).child("likedBy")
                    .child(currentUid).setValue(likes).await()
                emit(Resource.Success(Any()))
            } catch (e: Exception) {
                println(e)
                emit(Resource.Error(message = e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun saveMatchToCrush(
        crushUserId: String,
        firstName: String,
        locationName: String,
        years: String,
        lat: String,
        long: String,
        profileImage: String
    ): Flow<Resource<Any>> {
        return flow {
            emit(Resource.Loading())
            try {
                val currentUid = auth.uid!!
                val match = Likes(
                    id = currentUid,
                    date = System.currentTimeMillis(),
                    firstName = firstName,
                    locationName = locationName,
                    years = years,
                    lat = lat,
                    long = long,
                    profileImage = profileImage,
                    matched = true
                )
                dbRef.child(MATCHES).child(crushUserId).child(currentUid).setValue(match).await()
                emit(Resource.Success(Any()))
            } catch (e: Exception) {
                println(e)
                emit(Resource.Error(message = e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun saveMatchToCurrentUser(
        crushUserId: String,
        firstName: String,
        locationName: String,
        years: String,
        lat: String,
        long: String,
        profileImage: String
    ): Flow<Resource<Any>> {
        return flow {
            emit(Resource.Loading())
            try {
                val currentUid = auth.uid!!
                val match = Likes(
                    id = crushUserId,
                    date = System.currentTimeMillis(),
                    firstName = firstName,
                    locationName = locationName,
                    years = years,
                    lat = lat,
                    long = long,
                    profileImage = profileImage,
                    matched = true
                )
                dbRef.child(MATCHES).child(currentUid).child(crushUserId).setValue(match).await()
                dbRef.child(LIKES).child(currentUid).child(crushUserId).setValue(match).await()
                emit(Resource.Success(Any()))
            } catch (e: Exception) {
                println(e)
                emit(Resource.Error(message = e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getAllUsers(): Flow<Resource<List<User>>> {
        return flow<Resource<List<User>>> {
            emit(Resource.Loading())
            try {
                val allUserProfiles = ArrayList<User>()
                val allUsers = dbRef.child(USER_PATH).get().await()

                for (i in allUsers.children) {
                    val result = i.getValue(User::class.java)
                    allUserProfiles.add(result!!)
                }
                emit(Resource.Success(allUserProfiles))
            } catch (e: Exception) {
                emit(Resource.Error(message = e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getAllLikedBy(currentUserId: String): Flow<Resource<List<Likes>>> {
        return flow<Resource<List<Likes>>> {
            emit(Resource.Loading())
            try {
                val allUserLikedBy = ArrayList<Likes>()
                val allLikes = dbRef.child(LIKES).child(currentUserId).get().await()

                for (i in allLikes.children) {
                    val result = i.getValue(Likes::class.java)
                    allUserLikedBy.add(result!!)
                }
                emit(Resource.Success(allUserLikedBy))
            } catch (e: Exception) {
                emit(Resource.Error(message = e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    companion object {
        const val USER_PATH = "WeDateUsers"
        const val LIKES = "Likes"
        const val MATCHES = "Matches"
    }
}
