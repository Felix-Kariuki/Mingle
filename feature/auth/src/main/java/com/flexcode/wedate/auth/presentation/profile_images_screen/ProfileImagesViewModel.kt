/*
 * Copyright 2023 Felix Kariuki.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.flexcode.wedate.auth.presentation.profile_images_screen

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.flexcode.wedate.auth.data.local.datastore.AuthDataStore
import com.flexcode.wedate.auth.domain.model.Response
import com.flexcode.wedate.auth.domain.model.Response.Loading
import com.flexcode.wedate.auth.domain.model.Response.Success
import com.flexcode.wedate.auth.domain.repository.AuthRepository
import com.flexcode.wedate.auth.domain.repository.ProfileImageRepository
import com.flexcode.wedate.common.BaseViewModel
import com.flexcode.wedate.common.data.LogService
import com.flexcode.wedate.common.navigation.HOME_SCREEN_CONTENT
import com.flexcode.wedate.common.navigation.PROFILE_IMAGES_SCREEN
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class ProfileImagesViewModel @Inject constructor(
    logService: LogService,
    private val dataStore: AuthDataStore,
    private val repository: ProfileImageRepository,
    private val authRepository: AuthRepository
) : BaseViewModel(logService) {

    var addImageToStorageResponse by mutableStateOf<Response<Uri>>(Success(null))
        private set
    var addImageToDatabaseResponse by mutableStateOf<Response<Boolean>>(Success(null))
        private set

    fun onContinueClicked(openAndPopUp: (String, String) -> Unit) {
        launchCatching { openAndPopUp(HOME_SCREEN_CONTENT, PROFILE_IMAGES_SCREEN) }
    }

    fun addImageToStorage(imageUri: Uri, imageNumber: String) = viewModelScope.launch {
        addImageToStorageResponse = Loading
        addImageToStorageResponse = repository.uploadImageToFirebaseStorage(imageUri, imageNumber)
    }

    /*fun addImageToFirestoreDatabase(downloadUrl: Uri,user: User) = viewModelScope.launch {
        addImageToDatabaseResponse = Loading
        addImageToDatabaseResponse = repository.uploadImageUrlToFirestore(downloadUrl,user)
    }*/
}
