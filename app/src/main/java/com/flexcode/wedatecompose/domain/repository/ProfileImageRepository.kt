package com.flexcode.wedatecompose.domain.repository

import android.net.Uri
import com.flexcode.wedatecompose.data.models.User
import com.flexcode.wedatecompose.domain.model.Response
import kotlinx.coroutines.flow.Flow

interface ProfileImageRepository {

    suspend fun uploadImageToFirebaseStorage(imageUri: Uri,imageNumber:String): Response<Uri>

    suspend fun uploadImageUrlToFirestore(downloadUrl: Uri,user: User): Response<Boolean>

    /*suspend fun getImageUrlFromFirestore(): Response<String>*/
}