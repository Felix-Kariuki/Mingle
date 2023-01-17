package com.flexcode.wedatecompose.data.repository

import com.flexcode.wedatecompose.data.models.User
import kotlinx.coroutines.flow.Flow

interface StoreRegistrationRepository {
    val user: Flow<List<User>>

    suspend fun getUserDetails(userId:String): User?
    suspend fun saveUserDetails(user: User): String
    suspend fun updateUserDetails(user: User)
}