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
package com.flexcode.wedate.profileedit.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.flexcode.wedate.auth.presentation.profile_images_screen.ProfileImageState
import com.flexcode.wedate.common.BaseViewModel
import com.flexcode.wedate.common.data.LogService
import com.flexcode.wedate.common.snackbar.SnackBarManager
import com.flexcode.wedate.common.utils.Resource
import com.flexcode.wedatecompose.network.domain.use_cases.auth.UseCaseContainer
import com.flexcode.wedatecompose.network.domain.use_cases.profile_edit.EditUseCaseContainer
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val editProfileUseCaseContainer: EditUseCaseContainer,
    logService: LogService,
    private val useCaseContainer: UseCaseContainer,
    private val auth: FirebaseAuth
) : BaseViewModel(logService) {
    var state = mutableStateOf(EditProfileState())
        private set

    private var profileDetailsState = mutableStateOf(ProfileImageState())

    init {
        getUserDetails()
    }

    fun getUserDetails() {
        viewModelScope.launch {
            useCaseContainer.getUserDetailsUseCase(auth.uid!!).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        profileDetailsState.value = profileDetailsState.value.copy(
                            userDetails = result.data
                        )
                        updateEditState()
                    }

                    is Resource.Loading -> {}
                    is Resource.Error -> {}
                }
            }
        }
    }

    private fun updateEditState() {
        state.value =
            state.value.copy(userBio = profileDetailsState.value.userDetails?.userBio ?: "")
        state.value =
            state.value.copy(nickName = profileDetailsState.value.userDetails?.nickName ?: "")
    }

    fun onUserBioChange(value: String) {
        state.value = state.value.copy(userBio = value)
    }

    fun onUserNickNameChange(value: String) {
        state.value = state.value.copy(nickName = value)
    }

    fun updateUserProfile(userBio: String, nickName: String) {
        viewModelScope.launch {
            editProfileUseCaseContainer.editProfileUseCase.invoke(
                userBio = userBio,
                nickName = nickName
            )
                .collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            SnackBarManager.showError("Updated Successfully")
                            state.value = state.value.copy(isEdited = true)
                        }

                        is Resource.Loading -> {}

                        is Resource.Error -> {
                            SnackBarManager.showError(result.message.toString())
                        }
                    }
                }
        }
    }
}
