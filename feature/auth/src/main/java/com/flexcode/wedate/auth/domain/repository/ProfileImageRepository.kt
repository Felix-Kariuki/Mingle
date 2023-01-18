package com.flexcode.wedate.auth.domain.repository

import android.net.Uri
import com.flexcode.wedate.auth.data.models.User
import com.flexcode.wedate.auth.domain.model.Response

interface ProfileImageRepository {

    suspend fun uploadImageToFirebaseStorage(imageUri: Uri,imageNumber:String): Response<Uri>

    suspend fun uploadImageUrlToFirestore(downloadUrl: Uri,user: User): Response<Boolean>

    /*suspend fun getImageUrlFromFirestore(): Response<String>*/
}