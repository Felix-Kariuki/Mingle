package com.flexcode.wedate.auth.data.repository

import androidx.compose.runtime.MutableState
import com.flexcode.wedate.auth.data.models.ProfileImage
import com.flexcode.wedate.auth.data.models.User
import com.flexcode.wedate.auth.domain.repository.AuthRepository
import com.flexcode.wedate.common.utils.Resource
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import java.lang.IllegalArgumentException
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
) : AuthRepository {

    private val ref = FirebaseDatabase.getInstance().reference.child(USER_PATH)
    private val dbRef = FirebaseDatabase.getInstance().reference

    override val userIsAnonymous: Boolean
        get() = auth.currentUser?.isAnonymous == true

    override val currentUserId: String
        get() = auth.currentUser?.uid.orEmpty()

    override val hasUser: Boolean
        get() = auth.currentUser != null

    override val currentUser: Flow<User>
        get() = callbackFlow {
            val listener =
                FirebaseAuth.AuthStateListener { auth ->
                    this.trySend(auth.currentUser?.let {
                        User(id = it.uid, isAnonymous = it.isAnonymous)
                    } ?: User())
                }
            auth.addAuthStateListener(listener)
            awaitClose { auth.removeAuthStateListener(listener) }
        }

    override suspend fun createAnonymousAccount() {
        auth.signInAnonymously().await()
    }

    override suspend fun signOut() {
        if (auth.currentUser!!.isAnonymous) {
            auth.currentUser!!.delete()
        }
        auth.signOut()
        //createAnonymousAccount()
    }

    override suspend fun login(email: String, password: String): Flow<Resource<AuthResult>> {

        return flow {
            emit(Resource.Loading())

            try {
                val result = auth.signInWithEmailAndPassword(email, password).await()
                emit(Resource.Success(result))
            } catch (e: Exception) {
                emit(Resource.Error(message = e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)

    }


    override suspend fun register(
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
    ): Flow<Resource<AuthResult>> {
        return flow {
            emit(Resource.Loading())

            try {
                val result = auth.createUserWithEmailAndPassword(email, password).await()
                //auth.currentUser?.sendEmailVerification()?.await()
                val uid = result.user?.uid
                val user = User(
                    id = uid!!,
                    isAnonymous = false,
                    firstName = firstName,
                    phoneNumber = phoneNumber,
                    email = email,
                    password = password,
                    dateOfBirth = dateOfBirth,
                    monthOfBirth = monthOfBirth,
                    yearOfBirth = yearOfBirth,
                    years = years,
                    gender = gender,
                    interestedIn = interestedIn,
                    searchingFor = searchingFor,
                    isFree = false,
                    datingStatus = "Single"
                )
                ref.child(uid).setValue(user).await()
                val profileImage = ProfileImage()
                ref.child(uid).child("profileImage").setValue(profileImage)
                emit(Resource.Success(result))

            } catch (e: Exception) {
                emit(Resource.Error(message = e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getCurrentUserDetails(): Flow<Resource<User>> {
        return flow {
            emit(Resource.Loading())
            try {
                val uid = auth.uid!!
                val currentUser = dbRef.child(USER_PATH).child(uid).get().await()
                    .getValue(User::class.java) ?: throw IllegalArgumentException()
                emit(Resource.Success(currentUser))
            } catch (e: Exception) {
                emit(Resource.Error(message = e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun updateUserProfileInfo(
        longitude: MutableState<Double>,
        latitude: MutableState<Double>,
        locationName: String
    ): Flow<Resource<Any>> {
        return flow {
            emit(Resource.Loading())
            try {
                val uid = auth.uid!!
                dbRef.child(USER_PATH).child(uid).child("locationName").setValue(locationName).await()
                emit(Resource.Success(Any()))
            } catch (e: Exception) {
                println(e)
                emit(Resource.Error(message = e.message.toString()))
            }

        }.flowOn(Dispatchers.IO)
    }

    override suspend fun sendRecoveryEmail(email: String) {
        auth.sendPasswordResetEmail(email).await()
    }

    override suspend fun deleteAccount() {
        auth.currentUser!!.delete().await()
    }

    companion object {
        const val USER_PATH = "WeDateUsers"
    }
}