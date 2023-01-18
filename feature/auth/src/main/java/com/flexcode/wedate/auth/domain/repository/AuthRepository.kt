package com.flexcode.wedate.auth.domain.repository

import com.flexcode.wedate.auth.data.models.User
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    val currentUserId: String
    val hasUser: Boolean
    val userIsAnonymous: Boolean
    val currentUser: Flow<User>

    suspend fun createAnonymousAccount()
    suspend fun signOut()
    suspend fun login(email:String, password:String)
    suspend fun register(email:String, password: String)
    suspend fun sendRecoveryEmail(email:String)
    suspend fun deleteAccount()
}