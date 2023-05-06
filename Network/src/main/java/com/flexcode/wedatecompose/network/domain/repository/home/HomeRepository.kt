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
package com.flexcode.wedatecompose.network.domain.repository.home

import com.flexcode.wedate.common.utils.Resource
import com.flexcode.wedatecompose.network.data.models.auth.User
import com.flexcode.wedatecompose.network.data.models.home.Likes
import kotlinx.coroutines.flow.Flow

interface HomeRepository {

    suspend fun updateUserProfileInfo(
        longitude: String,
        latitude: String,
        locationName: String
    ): Flow<Resource<Any>>

    suspend fun updateUserAge(years: String): Flow<Resource<Any>>

    suspend fun getAllUsers(): Flow<Resource<List<User>>>

    suspend fun saveLike(
        crushUserId: String,
        firstName: String,
        locationName: String,
        years: String,
        lat: String,
        long: String,
        profileImage: String,
        matched: Boolean
    ): Flow<Resource<Any>>

    suspend fun getAllLikedBy(currentUserId: String): Flow<Resource<List<Likes>>>

    suspend fun saveMatchToCurrentUser(
        crushUserId: String,
        firstName: String,
        locationName: String,
        years: String,
        lat: String,
        long: String,
        profileImage: String
    ): Flow<Resource<Any>>

    suspend fun saveMatchToCrush(
        crushUserId: String,
        firstName: String,
        locationName: String,
        years: String,
        lat: String,
        long: String,
        profileImage: String
    ): Flow<Resource<Any>>

    suspend fun deleteLikedByFromMe(userLikeId: String): Flow<Resource<Any>>
}
