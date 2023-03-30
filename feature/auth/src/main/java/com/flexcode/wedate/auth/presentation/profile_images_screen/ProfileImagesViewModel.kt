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
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.flexcode.wedate.auth.domain.usecase.UseCaseContainer
import com.flexcode.wedate.common.BaseViewModel
import com.flexcode.wedate.common.R
import com.flexcode.wedate.common.data.LogService
import com.flexcode.wedate.common.navigation.HOME_SCREEN_CONTENT
import com.flexcode.wedate.common.navigation.PROFILE_IMAGES_SCREEN
import com.flexcode.wedate.common.snackbar.SnackBarManager
import com.flexcode.wedate.common.utils.Resource
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class ProfileImagesViewModel @Inject constructor(
    private val useCaseContainer: UseCaseContainer,
    private val auth: FirebaseAuth,
    logService: LogService
) : BaseViewModel(logService) {

    var state = mutableStateOf(ProfileImageState())
        private set

    init {
        getUserDetails()
    }
    private fun getUserDetails() {
        viewModelScope.launch {
            useCaseContainer.getUserDetailsUseCase(auth.uid!!).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        state.value = state.value.copy(
                            userDetails = result.data
                        )
                    }
                    is Resource.Loading -> {}
                    is Resource.Error -> {}
                }
            }
        }
    }

    fun onContinueClicked(openAndPopUp: (String, String) -> Unit) {
        if (state.value.userDetails?.profileImage?.profileImage1 == "" ||
            state.value.userDetails?.profileImage?.profileImage2 == ""
        ) {
            SnackBarManager.showMessage(R.string.profile_image_error)
            return
        }
        launchCatching { openAndPopUp(HOME_SCREEN_CONTENT, PROFILE_IMAGES_SCREEN) }
    }

    fun addImageToFirebaseStorage(imageUri: Uri, imageNumber: String) {
        viewModelScope.launch {
            useCaseContainer.profileImageUseCase.invoke(imageUri, imageNumber).collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        state.value = state.value.copy(isLoading = "true")
                    }
                    is Resource.Success -> {
                        state.value = state.value.copy(isLoading = "false")
                        val imageUrl = result.data.toString()
                        viewModelScope.launch {
                            useCaseContainer.updateProfileImageUseCase.invoke(imageUrl, imageNumber)
                                .collect { result ->
                                    when (result) {
                                        is Resource.Loading -> {
                                            state.value = state.value.copy(isLoading = "true")
                                        }
                                        is Resource.Success -> {
//                                            SnackBarManager.showError(
//                                                "$imageNumber Uploaded Successfully"
//                                            )
                                            getUserDetails()
                                            state.value = state.value.copy(isLoading = "false")
                                        }
                                        is Resource.Error -> {
                                            SnackBarManager.showError("${result.message}")
                                        }
                                    }
                                }
                        }
                    }
                    is Resource.Error -> {
                        SnackBarManager.showError("${result.message}")
                    }
                }
            }
        }
    }
}
