package com.flexcode.wedate.auth.presentation.profile_images_screen

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.flexcode.wedate.common.BaseViewModel
import com.flexcode.wedate.common.navigation.HOME_SCREEN_CONTENT
import com.flexcode.wedate.common.navigation.PROFILE_IMAGES_SCREEN
import com.flexcode.wedate.auth.data.local.datastore.AuthDataStore
import com.flexcode.wedate.auth.data.models.User
import com.flexcode.wedate.auth.domain.model.Response
import com.flexcode.wedate.auth.domain.model.Response.Success
import com.flexcode.wedate.auth.domain.model.Response.Loading
import com.flexcode.wedate.auth.domain.repository.ProfileImageRepository
import com.flexcode.wedate.common.data.LogService
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
        launchCatching { openAndPopUp(HOME_SCREEN_CONTENT, PROFILE_IMAGES_SCREEN) }
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
