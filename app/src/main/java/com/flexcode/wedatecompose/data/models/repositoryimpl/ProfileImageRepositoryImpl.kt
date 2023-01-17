package com.flexcode.wedatecompose.data.models.repositoryimpl

import android.net.Uri
import com.flexcode.wedatecompose.data.models.User
import com.flexcode.wedatecompose.data.repository.AuthRepository
import com.flexcode.wedatecompose.domain.model.Response
import com.flexcode.wedatecompose.domain.model.Response.Failure
import com.flexcode.wedatecompose.domain.model.Response.Success
import com.flexcode.wedatecompose.domain.repository.ProfileImageRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.snapshots
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton


class ProfileImageRepositoryImpl @Inject constructor(
    private val storage: FirebaseStorage,
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
) : ProfileImageRepository {

    /*override val user: Flow<List<User>>
        get() = authRepository.currentUser.flatMapLatest { user->
            currentCollection(user.id)
                .snapshots()
                .map { snapshot -> snapshot.toObjects() }
        }*/


    override suspend fun uploadImageToFirebaseStorage(imageUri: Uri,imageNumber:String): Response<Uri> {
        return try {
            val downloadUrl = storage.reference.child(IMAGES).child(auth.currentUser?.uid.toString())
                .child("$imageNumber.jpg")
                .putFile(imageUri).await()
                .storage.downloadUrl.await()
            Success(downloadUrl)
        } catch (e: Exception) {
            Failure(e)
        }
    }

    override suspend fun uploadImageUrlToFirestore(downloadUrl: Uri,user:User): Response<Boolean> {
        return try {
            firestore.collection("users").document(auth.currentUser?.uid.toString())
                .collection("details").document(user.id).set(mapOf("url" to downloadUrl,
                    "createdAt" to FieldValue.serverTimestamp()
                )).await()
            Success(true)
        }catch (e:Exception){
            Failure(e)
        }
    }

    /*override suspend fun getImageUrlFromFirestore(): Response<String> {
        return try {

        }
    }*/

    private fun currentCollection(id:String): CollectionReference {
        return  firestore.collection("users").document(id).collection("details")
    }

    companion object {
        const val IMAGES = "WeDateImages"
    }
}