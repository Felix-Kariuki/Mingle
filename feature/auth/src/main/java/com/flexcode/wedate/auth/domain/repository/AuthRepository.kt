package com.flexcode.wedate.auth.domain.repository

import com.flexcode.wedate.auth.data.models.User
import com.flexcode.wedate.common.utils.Resource
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    val currentUserId: String
    val hasUser: Boolean
    val userIsAnonymous: Boolean
    val currentUser: Flow<User>

    suspend fun createAnonymousAccount()
    suspend fun signOut()
    suspend fun login(email:String, password:String): Flow<Resource<AuthResult>>

    suspend fun register(firstName:String, phoneNumber:String, email:String, password: String, dateOfBirth:String,
                         monthOfBirth:String, yearOfBirth:String, years:String, gender:String, interestedIn:String,
                         searchingFor:String,
                       ): Flow<Resource<AuthResult>>
    suspend fun sendRecoveryEmail(email:String)
    suspend fun deleteAccount()

    suspend fun getCurrentUserDetails(): Flow<Resource<User>>
}