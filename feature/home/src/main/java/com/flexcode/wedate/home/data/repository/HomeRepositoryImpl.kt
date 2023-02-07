package com.flexcode.wedate.home.data.repository

import com.flexcode.wedate.home.data.model.Likes
import com.flexcode.wedate.auth.data.models.User
import com.flexcode.wedate.auth.data.repository.AuthRepositoryImpl
import com.flexcode.wedate.common.utils.Resource
import com.flexcode.wedate.home.domain.repository.HomeRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class HomeRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
) : HomeRepository{


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
                dbRef.child(AuthRepositoryImpl.USER_PATH).child(uid).child("locationName").setValue(locationName).await()
                dbRef.child(AuthRepositoryImpl.USER_PATH).child(uid).child("longitude").setValue(longitude).await()
                dbRef.child(AuthRepositoryImpl.USER_PATH).child(uid).child("latitude").setValue(latitude).await()
                emit(Resource.Success(Any()))
            } catch (e: Exception) {
                println(e)
                emit(Resource.Error(message = e.message.toString()))
            }

        }.flowOn(Dispatchers.IO)
    }

    //move to feature home
    override suspend fun saveLike(crushUserId: String): Flow<Resource<Any>> {
        return flow {
            emit(Resource.Loading())
            try {
                val likeId = UUID.randomUUID().toString()
                val currentUid = auth.uid!!
                val likes = Likes(
                    id = currentUid,
                    date = System.currentTimeMillis()
                )
                dbRef.child(AuthRepositoryImpl.LIKES).child(crushUserId).child(currentUid).setValue(likes).await()
                dbRef.child(AuthRepositoryImpl.USER_PATH).child(crushUserId).child("likedBy").child(currentUid).setValue(likes).await()
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
                val allUsers = dbRef.child(AuthRepositoryImpl.USER_PATH).get().await()

                for (i in allUsers.children){
                    val result = i.getValue(User::class.java)
                    allUserProfiles.add(result!!)
                }
                emit(Resource.Success(allUserProfiles))
            } catch (e: Exception) {
                emit(Resource.Error(message = e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    companion object {
        const val USER_PATH = "WeDateUsers"
        const val LIKES = "Likes"
    }
}