package com.flexcode.wedate.auth.domain.repository

import com.flexcode.wedate.auth.data.models.User
import kotlinx.coroutines.flow.Flow

interface StoreRegistrationRepository {
    val user: Flow<List<User>>

    suspend fun getUserDetails(userId:String): User?
    suspend fun saveUserDetails(user: User): String
    suspend fun updateUserDetails(user: User)
}