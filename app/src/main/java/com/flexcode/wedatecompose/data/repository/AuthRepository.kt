package com.flexcode.wedatecompose.data.repository

import com.flexcode.wedatecompose.data.models.User
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    val currentUserId: String
    val hasUser: Boolean
    val currentUser: Flow<User>

    suspend fun createAnonymousAccount()
    suspend fun signOut()
    suspend fun login(email:String, password:String)
    suspend fun register(email:String, password: String)
    suspend fun sendRecoveryEmail(email:String)
    suspend fun deleteAccount()
}