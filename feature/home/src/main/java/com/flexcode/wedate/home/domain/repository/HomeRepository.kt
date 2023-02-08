package com.flexcode.wedate.home.domain.repository

import com.flexcode.wedate.auth.data.models.User
import com.flexcode.wedate.common.utils.Resource
import com.flexcode.wedate.home.data.model.Likes
import kotlinx.coroutines.flow.Flow

interface HomeRepository {

    suspend fun updateUserProfileInfo(
        longitude: String, latitude: String, locationName: String
    ): Flow<Resource<Any>>

    suspend fun getAllUsers(): Flow<Resource<List<User>>>

    suspend fun saveLike(
        crushUserId: String, firstName: String, locationName: String, years: String,
        lat: String, long: String, profileImage: String
    ): Flow<Resource<Any>>

    suspend fun getAllLikedBy(currentUserId:String) : Flow<Resource<List<Likes>>>

    suspend fun saveMatchToCurrentUser(
        crushUserId: String, firstName: String, locationName: String, years: String,
        lat: String, long: String, profileImage: String
    ): Flow<Resource<Any>>

    suspend fun saveMatchToCrush(
        crushUserId: String, firstName: String, locationName: String, years: String,
        lat: String, long: String, profileImage: String
    ): Flow<Resource<Any>>

}