package com.flexcode.wedatecompose.presentation.profile_images_screen

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.flexcode.wedatecompose.common.BaseViewModel
import com.flexcode.wedatecompose.common.navigation.HOME_SCREEN
import com.flexcode.wedatecompose.common.navigation.HOME_SCREEN_CONTENT
import com.flexcode.wedatecompose.common.navigation.PROFILE_IMAGES_SCREEN
import com.flexcode.wedatecompose.data.LogService
import com.flexcode.wedatecompose.data.local.datastore.AuthDataStore
import com.flexcode.wedatecompose.data.models.User
import com.flexcode.wedatecompose.domain.model.Response
import com.flexcode.wedatecompose.domain.model.Response.Success
import com.flexcode.wedatecompose.domain.model.Response.Loading
import com.flexcode.wedatecompose.domain.repository.ProfileImageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileImagesViewModel @Inject constructor(
    logService: LogService,
    private val dataStore: AuthDataStore,
    private val repository: ProfileImageRepository
): BaseViewModel(logService) {

    var addImageToStorageResponse by mutableStateOf<Response<Uri>>(Success(null))
        private set
    var addImageToDatabaseResponse by mutableStateOf<Response<Boolean>>(Success(null))
        private set

    fun onContinueClicked(openAndPopUp:(String,String)->Unit){
        launchCatching { openAndPopUp(HOME_SCREEN_CONTENT,PROFILE_IMAGES_SCREEN) }
    }

    fun addImageToStorage(imageUri: Uri,imageNumber:String) = viewModelScope.launch {
        addImageToStorageResponse = Loading
        addImageToStorageResponse = repository.uploadImageToFirebaseStorage(imageUri,imageNumber)

    }

    fun addImageToFirestoreDatabase(downloadUrl: Uri,user: User) = viewModelScope.launch {
        addImageToDatabaseResponse = Loading
        addImageToDatabaseResponse = repository.uploadImageUrlToFirestore(downloadUrl,user)
    }

}
