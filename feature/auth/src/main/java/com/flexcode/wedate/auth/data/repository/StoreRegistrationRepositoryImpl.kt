package com.flexcode.wedate.auth.data.repository

import com.flexcode.wedate.auth.data.models.User
import com.flexcode.wedate.auth.domain.repository.AuthRepository
import com.flexcode.wedate.auth.domain.repository.StoreRegistrationRepository
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.snapshots
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class StoreRegistrationRepositoryImpl @Inject constructor(

    private val firestore: FirebaseFirestore,
    private val authRepository: AuthRepository
): StoreRegistrationRepository {


    override val user: Flow<List<User>>
        get() = authRepository.currentUser.flatMapLatest { user->
            currentCollection(user.id)
                .snapshots()
                .map { snapshot -> snapshot.toObjects() }
        }

    override suspend fun getUserDetails(userId: String): User? {
        return currentCollection(authRepository.currentUserId).document(userId).get().await().toObject()
    }

    override suspend fun saveUserDetails(user: User): String {
        return currentCollection(authRepository.currentUserId).add(user).await().id
    }

    override suspend fun updateUserDetails(user: User) {
        currentCollection(authRepository.currentUserId).document(user.id).set(user).await()
    }

    private fun currentCollection(id:String): CollectionReference {
        return firestore.collection(USER_COLLECTION).document(id).collection(USER_DETAILS_COLLECTION)
    }

    companion object{
        const val USER_COLLECTION = "users"
        const val USER_DETAILS_COLLECTION = "details"
    }
}