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
package com.flexcode.wedatecompose.network.domain.repository.auth

import com.flexcode.wedate.common.utils.Resource
import com.flexcode.wedatecompose.network.data.models.auth.User
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    val currentUserId: String
    val hasUser: Boolean
    val userIsAnonymous: Boolean
    val currentUser: Flow<User>

    fun signOut()
    suspend fun login(email: String, password: String): Flow<Resource<AuthResult>>

    fun deleteUser()

    suspend fun register(
        firstName: String,
        phoneNumber: String,
        email: String,
        password: String,
        dateOfBirth: String,
        monthOfBirth: String,
        yearOfBirth: String,
        years: String,
        gender: String,
        interestedIn: String,
        searchingFor: String
    ): Flow<Resource<AuthResult>>
    suspend fun sendRecoveryEmail(email: String)
    suspend fun deleteAccount(accountStatus: String): Flow<Resource<Any>>

    suspend fun getCurrentUserDetails(uid: String): Flow<Resource<User>>
}
