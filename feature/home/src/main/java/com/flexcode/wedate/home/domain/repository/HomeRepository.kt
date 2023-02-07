package com.flexcode.wedate.home.domain.repository

import com.flexcode.wedate.auth.data.models.User
import com.flexcode.wedate.common.utils.Resource
import kotlinx.coroutines.flow.Flow

interface HomeRepository {

    suspend fun updateUserProfileInfo(
        longitude: String, latitude: String, locationName:String
    ): Flow<Resource<Any>>

    suspend fun getAllUsers(): Flow<Resource<List<User>>>

    suspend fun saveLike(crushUserId:String): Flow<Resource<Any>>

}